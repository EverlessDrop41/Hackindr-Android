package com.everlesslycoding.hackindr;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;

/**
 * Created by emilyperegrine on 19/12/2015.
 */
public class HttpOperations {
    OkHttpClient client;

    HttpOperations () {
        client = new OkHttpClient();
    }
    // code request code here
    ResponseBody doGetRequest(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body();
    }

    ResponseBody doPostRequest(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(MediaType.parse("JSON"), json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body();
    }
}
