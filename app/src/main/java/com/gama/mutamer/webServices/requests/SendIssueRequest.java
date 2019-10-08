package com.gama.mutamer.webServices.requests;


import com.gama.mutamer.R;
import com.gama.mutamer.utils.Constants;
import com.gama.mutamer.viewModels.webServices.PostParam;
import com.gama.mutamer.webServices.params.SendIssueParam;

import java.util.ArrayList;

/**
 * Created by mustafa on 8/24/16.
 * Release the GEEK
 */
public class SendIssueRequest extends MultipartBaseRequest {

    private SendIssueParam mParam;

    public SendIssueRequest() {

    }

    public SendIssueRequest(String accessToken, SendIssueParam param) {
        setParam(param);
        setAccessToken(accessToken);
    }

    @Override
    public int getServiceUrl() {
        return R.string.url_report_issue;
    }

    @Override
    public ArrayList<PostParam> getData() {
        ArrayList<PostParam> data = new ArrayList<>();
        data.add(new PostParam(Constants.TITLE, getParam().getTitle()));
        data.add(new PostParam(Constants.BODY, getParam().getBody()));
        return data;
    }

    public SendIssueParam getParam() {
        return mParam;
    }

    public void setParam(SendIssueParam param) {
        mParam = param;
    }
}
