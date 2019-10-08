package com.gama.mutamer.helpers;

import android.content.Context;
import android.os.Environment;

import com.gama.mutamer.utils.Constants;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GeneralUtils {

//    //change font family of text view
//    public static void changeFontFamily(Context context, View view) {
//
//        Typeface tf = Typeface.createFromAsset(context.getAssets(), "ubuntu-c.ttf");
//        if (view instanceof TextView)
//            ((TextView) view).setTypeface(tf, Typeface.NORMAL);
//    }
//
////    public static void logoff(Context context) {
////        new DatabaseHandler(context).ClearData();
////        context.stopService(new Intent(context, OmraUpdateService.class));
////        new ServicePost().DoPost(new RemovePushRequest(SharedPrefHelper.getSharedInt(context, SharedPrefHelper.PUSH_ID_KEY)), context);
////    }
//
//    public static void changeFontFamilyForLayout(Context context, View view) {
//
//        Typeface tf = Typeface.createFromAsset(context.getAssets(), "ubuntu-c.ttf");
//
//        if (view instanceof TextView)
//            ((TextView) view).setTypeface(tf, Typeface.NORMAL);
//        if (view instanceof ViewGroup) {
//            ViewGroup group = (ViewGroup) view;
//            for (int i = 0; i < group.getChildCount(); i++) {
//                if (group.getChildAt(i) instanceof ViewGroup)
//                    changeFontFamilyForLayout(context, group.getChildAt(i));
//                else if (group.getChildAt(i) instanceof TextView)
//                    ((TextView) group.getChildAt(i)).setTypeface(tf);
//            }
//        }
//    }
//
//    //check if string contains arabic characters
//    public static boolean isProbablyArabic(String s) {
//        for (int i = 0; i < s.length(); ) {
//            int c = s.codePointAt(i);
//            if (c >= 0x0600 && c <= 0x06E0)
//                return true;
//            i += Character.charCount(c);
//        }
//        return false;
//    }
//
//    //show menu on top bar
//    public static void showOverflowMenu(Activity activity) {
//        try {
//            ViewConfiguration config = ViewConfiguration.get(activity);
//            Field menuKeyField = ViewConfiguration.class
//                    .getDeclaredField("sHasPermanentMenuKey");
//            if (menuKeyField != null) {
//                menuKeyField.setAccessible(true);
//                menuKeyField.setBoolean(config, false);
//            }
//        } catch (Exception e) {
//           // OmraLog.e(e);
//        }
//    }


//    // check if this device has a camera
//    public static boolean checkCameraHardware(Context context) {
//        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
//    }
//
//    //dismiss keyboard in activity
//    public static void dismissKeyboard(Activity activity) {
//        if (activity.getCurrentFocus() == null)
//            return;
//
//        InputMethodManager imm = (InputMethodManager) activity
//                .getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
//    }

//    //change language of app
//    public static void setLocale(Context ctx, final String lang) {
//        final Locale loc = new Locale(lang);
//        Locale.setDefault(loc);
//        final Configuration cfg = new Configuration();
//        cfg.locale = loc;
//        ctx.getResources().updateConfiguration(cfg, null);
//    }
//
//    //check if language of device is arabic
//    public static boolean isNeededRTLLayout() {
//        return getLanguage().equals("ar");
//    }
//
//    //get language of device
//    public static String getLanguage() {
//        String lang = Locale.getDefault().getDisplayLanguage();
//        if (lang.contains("عربية") || lang.contains("arab")
//                || lang.toLowerCase().equalsIgnoreCase("ar")) {
//            lang = "ar";
//        } else {
//            lang = "en";
//        }
//        return lang;
//    }
//
//    //check if network is available and connected
//    public static boolean isNetworkAvailable(Context context) {
//        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
//        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
//    }
//
//
//
//    //convert string to date based on specific format
//    public static Date convertStringToDate(String dateString) {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyy hh:mm:ss a", Locale.ENGLISH);
//        Date convertedDate = new Date();
//        try {
//            convertedDate = dateFormat.parse(dateString);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return convertedDate;
//    }
//
//    public static String convertDateToString(Date date) {
//
//        try {
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
//            return dateFormat.format(date);
//        } catch (Exception ex) {
//           // OmraLog.e(ex);
//        }
//        return date.toString();
//    }

//    //get difference in minutes between a specified date and now
//    public static long getDifferenceFromNowInMinutes(Date date) {
//
//        try {
//            long nowMillis = new Date().getTime();
//            long dateMillis = date.getTime();
//            long diffInMillis = nowMillis - dateMillis;
//            if (diffInMillis < 0)
//                return -1;
//
//            return TimeUnit.MINUTES.toMinutes(diffInMillis);
//        } catch (Exception ex) {
//           // OmraLog.e(ex);
//        }
//        return -1;
//    }

    //create media file based on it's type
    public static File getOutputMediaFile(Context context, int type, String dir) {

        File mediaStorageDir = null;
        String name = null;
//
//        if (type == Constants.MEDIA_TYPE_IMAGE) {
//            name = context.getString(dir);
//            mediaStorageDir = new File(context.getFilesDir(), dir);
//        } else if (type == Constants.MEDIA_TYPE_VIDEO) {
//            name = context.getString(dir);
        mediaStorageDir = new File(Environment.getExternalStorageDirectory(), dir);
//        }
//        if (name == null)
//            return null;

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                // OmraLog.w("failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
//        if (type == Constants.MEDIA_TYPE_IMAGE) {
        String ext = (type == Constants.MEDIA_TYPE_IMAGE) ? ".jpg" : ".mp4";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + timeStamp + ext);
//        } else {
//            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
//                    "VID_" + timeStamp + ".mp4");
//        }

        return mediaFile;
    }

//    //get file content uri
//    public static Uri getContentUri(Context context, File imageFile,
//                                    boolean isImage) throws Exception {
//        Cursor cursor = null;
//        try {
//            String filePath = imageFile.getAbsolutePath();
//
//            if (isImage) {
//                cursor = context.getContentResolver().query(
//                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                        new String[]{MediaStore.Images.Media._ID},
//                        MediaStore.Images.Media.DATA + "=? ",
//                        new String[]{filePath}, null);
//                if (cursor != null && cursor.moveToFirst()) {
//                    int id = cursor.getInt(cursor
//                            .getColumnIndex(MediaStore.MediaColumns._ID));
//                    Uri baseUri = Uri
//                            .parse("content://media/external/images/media");
//                    return Uri.withAppendedPath(baseUri, "" + id);
//                } else {
//                    if (imageFile.exists()) {
//                        ContentValues values = new ContentValues();
//                        values.put(MediaStore.Images.Media.DATA, filePath);
//                        return context.getContentResolver().insert(
//                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                                values);
//                    } else {
//                        return null;
//                    }
//                }
//            } else {
//                cursor = context.getContentResolver().query(
//                        MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
//                        new String[]{MediaStore.Video.Media._ID},
//                        MediaStore.Video.Media.DATA + "=? ",
//                        new String[]{filePath}, null);
//                if (cursor != null && cursor.moveToFirst()) {
//                    int id = cursor.getInt(cursor
//                            .getColumnIndex(MediaStore.MediaColumns._ID));
//                    Uri baseUri = Uri.parse("content://media/external/video/media");
//                    return Uri.withAppendedPath(baseUri, "" + id);
//                } else {
//                    if (imageFile.exists()) {
//                        ContentValues values = new ContentValues();
//                        values.put(MediaStore.Video.Media.DATA, filePath);
//                        return context
//                                .getContentResolver()
//                                .insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
//                                        values);
//                    } else {
//                        return null;
//                    }
//                }
//            }
//        } finally {
//            if (cursor != null)
//                cursor.close();
//        }
//    }
//
//    // convert from bitmap to byte array
//    public static byte[] getBytesFromBitmap(Bitmap bitmap) {
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
//        return stream.toByteArray();
//    }
}
