package com.gama.mutamer.helpers.webService;

import android.content.Context;
import android.net.TrafficStats;
import android.util.Log;

import com.gama.mutamer.viewModels.utils.MediaItem;
import com.gama.mutamer.webServices.requests.BaseRequest;
import com.gama.mutamer.webServices.requests.SendIssueRequest;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPInputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ServicePost {
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    private final String TAG = "ServicePost";

    public ServicePost() {
        TrafficStats.setThreadStatsTag(77);
    }

    public ServiceResult DoPost(BaseRequest req, boolean login, Context context) {
        try {
            Log.v("Url", context.getString(req.getServiceUrl()));
            Log.v("Data", req.getData());
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();
            RequestBody body = RequestBody.create(JSON, req.getData());
            Request.Builder requestBuilder = new Request.Builder()
                    .url(context.getString(req.getServiceUrl()))
                    .post(body);
            if (!login)
                requestBuilder.addHeader("Authorization", "Bearer " + req.getAccessToken());
            final Response response = client.newCall(requestBuilder.build()).execute();
            String result = response.body().string();
            Log.v(TAG, result);
            return new ServiceResult(result);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ServiceResult();
        }
    }

    public ServiceResult DoPost(String _url) {
        ServiceResult result = new ServiceResult();
        try {
            URL url = new URL(_url);
            Log.v(TAG, url.toString());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            connection.setRequestProperty("Accept-Encoding", "gzip, deflate");
            InputStream inputStream;
//            Log.v(TAG, connection.getContentEncoding() + "");

            if (connection.getResponseCode() == 200) {
                inputStream = new BufferedInputStream((connection.getContentEncoding() != null && connection.getContentEncoding().equalsIgnoreCase("gzip")) ? new GZIPInputStream(connection.getInputStream()) : connection.getInputStream());
                result.setSuccess(true);
            } else {
                inputStream = new BufferedInputStream((connection.getContentEncoding() != null && connection.getContentEncoding().equalsIgnoreCase("gzip")) ? new GZIPInputStream(connection.getErrorStream()) : connection.getErrorStream());
                result.setSuccess(false);
            }
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader readStream = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = readStream.readLine()) != null)
                stringBuilder.append(line);
            Log.v(TAG, stringBuilder.toString());
            String res = stringBuilder.toString();
            result.setSuccess(true);
            result.setResult(res);
            inputStream.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            Log.v(TAG, "FAIL ");
        }
        return result;
    }

    public String DoPost(SendIssueRequest request, Context context, ArrayList<MediaItem> files) {
        try {
            final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
            final OkHttpClient client = new OkHttpClient();
            MultipartBody.Builder builder = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("Title", request.getParam().getTitle())
                    .addFormDataPart("Body", request.getParam().getBody());


            for (MediaItem item : files) {
                builder.addFormDataPart(item.getName(), item.getName(),
                        RequestBody.create(MEDIA_TYPE_PNG, new File(item.getFilePath())));
            }

            RequestBody requestBody = builder.build();

            Request Request = new Request.Builder()
                    .url(context.getString(request.getServiceUrl()))
                    .addHeader("Authorization","Bearer " + request.getAccessToken())
                    .post(requestBody)
                    .build();

            try (Response response = client.newCall(Request).execute()) {
                if (!response.isSuccessful()){
                    String res = response.body().string();
                    Log.v("ERROR", res);
                    return "Error";
                }
                String res = response.body().string();
                Log.v("RESPONSE", res);
                return res;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "Error";
    }


}
