package com.example.tzf.myapplication2;


import android.os.Handler;
import android.os.Looper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by fan on 2016/11/9.
 */

public class OkHttp {

    private static OkHttp sOkHttpManager;


    private OkHttpClient mClient;


    private Handler mHandler;


    private OkHttp() {

        mClient = new OkHttpClient();


        mClient.newBuilder().connectTimeout(10, TimeUnit.SECONDS);
        mClient.newBuilder().readTimeout(10, TimeUnit.SECONDS);
        mClient.newBuilder().writeTimeout(10, TimeUnit.SECONDS);


        mHandler = new Handler(Looper.getMainLooper());
    }



    public static OkHttp getInstance() {

        if (sOkHttpManager == null) {
            sOkHttpManager = new OkHttp();
        }
        return sOkHttpManager;
    }

    public static Response getSync(String url) {


        return sOkHttpManager.inner_getSync(url);
    }


    private Response inner_getSync(String url) {
        Request request = new Request.Builder().url(url).build();
        Response response = null;
        try {
            response = mClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }


    public static String getSyncString(String url) {
        return sOkHttpManager.inner_getSyncString(url);
    }


    private String inner_getSyncString(String url) {
        String result = null;
        try {

            result = inner_getSync(url).body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    public static void getAsync(String url, DataCallBack callBack) {
        getInstance().inner_getAsync(url, callBack);
    }


    private void inner_getAsync(String url, final DataCallBack callBack) {
        final Request request = new Request.Builder().url(url).build();

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                deliverDataFailure(request, e, callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = null;
                try {
                    result = response.body().string();
                } catch (IOException e) {
                    deliverDataFailure(request, e, callBack);
                }
                deliverDataSuccess(result, callBack);
            }
        });
    }



    private void deliverDataFailure(final Request request, final IOException e, final DataCallBack callBack) {

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    callBack.requestFailure(request, e);
                }
            }
        });
    }


    private void deliverDataSuccess(final String result, final DataCallBack callBack) {

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    try {
                        callBack.requestSuccess(result);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    public interface DataCallBack {
        void requestFailure(Request request, IOException e);

        void requestSuccess(String result) throws Exception;
    }


    public static void postAsync(String url, Map<String, String> params, DataCallBack callBack) {
        getInstance().inner_postAsync(url, params, callBack);
    }

    private void inner_postAsync(String url, Map<String, String> params, final DataCallBack callBack) {

        RequestBody requestBody = null;
        if (params == null) {
            params = new HashMap<>();
        }


        FormBody.Builder builder = new FormBody.Builder();


        for (Map.Entry<String, String> map : params.entrySet()) {
            String key = map.getKey().toString();
            String value = null;

            if (map.getValue() == null) {
                value = "";
            } else {
                value = map.getValue();
            }

            builder.add(key, value);
        }
        requestBody = builder.build();

        final Request request = new Request.Builder().url(url).post(requestBody).build();
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                deliverDataFailure(request, e, callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                deliverDataSuccess(result, callBack);
            }


        });
    }


    public static void downloadAsync(String url, String desDir, DataCallBack callBack) {
        getInstance().inner_downloadAsync(url, desDir, callBack);
    }


    private void inner_downloadAsync(final String url, final String desDir, final DataCallBack callBack) {
        final Request request = new Request.Builder().url(url).build();
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                deliverDataFailure(request, e, callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {


                InputStream inputStream = null;
                FileOutputStream fileOutputStream = null;
                try {

                    File file = new File(desDir, getFileName(url));

                    inputStream = response.body().byteStream();
                    fileOutputStream = new FileOutputStream(file);
                    int len = 0;
                    byte[] bytes = new byte[2048];
                    while ((len = inputStream.read(bytes)) != -1) {
                        fileOutputStream.write(bytes, 0, len);
                    }
                    fileOutputStream.flush();

                    deliverDataSuccess(file.getAbsolutePath(), callBack);
                } catch (IOException e) {
                    deliverDataFailure(request, e, callBack);
                    e.printStackTrace();
                } finally {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }

                }
            }

        });
    }


    private String getFileName(String url) {
        int separatorIndex = url.lastIndexOf("/");
        String path = (separatorIndex < 0) ? url : url.substring(separatorIndex + 1, url.length());
        return path;
    }


}

