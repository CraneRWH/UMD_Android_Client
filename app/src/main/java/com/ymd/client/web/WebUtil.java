package com.ymd.client.web;

import android.content.Context;
import android.os.Handler;

import com.google.gson.Gson;
import com.ymd.client.component.widget.dialog.LoadingDialog;
import com.ymd.client.model.constant.URLConstant;
import com.ymd.client.utils.CommonShared;
import com.ymd.client.utils.DialogUtil;
import com.ymd.client.utils.LogUtil;
import com.ymd.client.utils.ToastUtil;
import com.ymd.client.utils.ToolUtil;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * 调用网络接口
 *
 * @author rongweihe
 */

public class WebUtil {
    //测试，王斌的接口
//    public static String webUrl = "http://192.168.1.38:8080/ymd-rest-api/app/";

    public static String webUrl = "http://39.104.181.72:8095/ymd-rest-api/app/";


    private static volatile WebUtil mInstance;//单利引用
    private OkHttpClient mOkHttpClient;//okHttpClient 实例
    private Handler okHttpHandler;//全局处理子线程和M主线程通信

    private static Context appContext;

    public OkHttpClient getmOkHttpClient() {
        return mOkHttpClient;
    }

    /**
     * 初始化RequestManager
     */
    public WebUtil(Context context) {
        appContext = context;
        //初始化OkHttpClient
        mOkHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)//设置超时时间
                .readTimeout(10, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(10, TimeUnit.SECONDS)//设置写入超时时间
                .build();
        //初始化Handler
        okHttpHandler = new Handler(context.getMainLooper());
    }

    public static WebUtil initInstance(Context context) {
        synchronized (WebUtil.class) {
            if (mInstance == null) {
                mInstance = new WebUtil(context.getApplicationContext());
            }
        }
        return mInstance;
    }

    /**
     * 获取单例引用
     *
     * @return
     */
    public static WebUtil getInstance() {
        return mInstance;
    }

    /**
     * GET请求方式
     *
     * @param context  调用的Context
     * @param method   接口名称
     * @param params   额外添加的参数
     * @param isLogin  是否需要传递userId
     * @param isDialog 是否弹出dialog
     * @param callback 回调方法
     */
    public void requestGET(Context context, final String method,
                           Map<String, Object> params,  //附加信息
                           boolean isLogin,    //是否需要传递userId
                           boolean isDialog,   //是否显示弹出框
                           final WebCallBack callback) {

        appContext = context;
        if (params == null) {
            params = new HashMap<String, Object>();
        }

        if (isDialog) {
            showLoadingDialog(context);
        }

        Gson gson = new Gson();
        LogUtil.showW("▶▶ " + method + " ▶ " + gson.toJson(params));
        String GET_URL = attachHttpGetParams(webUrl + method, params);
        Request request = new Request.Builder()
                .url(GET_URL)
                .addHeader("token", CommonShared.getString(CommonShared.LOGIN_TOKEN,""))
                .get()
                .build();
        requestHttp(request, method, callback);
    }

    /**
     * POST请求方式
     *
     * @param context  调用的Context
     * @param method   接口名称
     * @param params   额外添加的参数
     * @param isLogin  是否需要登录，传递userId
     * @param isDialog 是否弹出dialog
     * @param callback 回调方法
     */
    public void requestPOST(Context context,
                            final String method,
                            Map<String, Object> params,  //附加信息
                            boolean isLogin,    //是否需要传递userId
                            boolean isDialog,   //是否显示弹出框
                            final WebCallBack callback) {

        RequestBody requestBody = paramsBuilder(context, method, params, isLogin, isDialog);
        Request request = new Request.Builder()
                .post(requestBody)
                .addHeader("token", CommonShared.getString(CommonShared.LOGIN_TOKEN,""))
                .url(webUrl + method)
                .build();
        requestHttp(request, method, callback);
    }

    /**
     * POST请求方式
     *
     * @param context  调用的Context
     * @param method   接口名称
     * @param params   额外添加的参数
     * @param callback 回调方法
     */
    public void requestPOST(Context context,
                            final String method,
                            Map<String, Object> params,  //附加信息
                            final WebCallBack callback) {

       requestPOST(context, method, params, true, true, callback);
    }

    /**
     * DELETE请求方式
     *
     * @param context  调用的Context
     * @param method   接口名称
     * @param params   额外添加的参数
     * @param isLogin  是否需要登录，传递userId
     * @param isDialog 是否弹出dialog
     * @param callback 回调方法
     */
    public void requestDELETE(Context context, final String method,
                              Map<String, Object> params,  //附加信息
                              boolean isLogin,    //是否需要传递userId
                              boolean isDialog,   //是否显示弹出框
                              final WebCallBack callback) {

        RequestBody requestBody = paramsBuilder(context, method, params, isLogin,isDialog);
        Request request = new Request.Builder()
                .post(requestBody)
                .addHeader("token", CommonShared.getString(CommonShared.LOGIN_TOKEN,""))
                .url(webUrl + method)
                .build();
        requestHttp(request, method, callback);
    }

    /**
     * PUT请求方式
     *
     * @param context  调用的Context
     * @param method   接口名称
     * @param params   额外添加的参数
     * @param isLogin  是否需要登录，传递userId
     * @param isDialog 是否弹出dialog
     * @param callback 回调方法
     */
    public void requestPUT(Context context,
                           final String method,
                           Map<String, Object> params,  //附加信息
                           boolean isLogin,    //是否需要传递userId
                           boolean isDialog,   //是否显示弹出框
                           final WebCallBack callback) {

        RequestBody requestBody = paramsBuilder(context, method, params,isLogin, isDialog);
        Request request = new Request.Builder()
                .put(requestBody)
                .addHeader("token", CommonShared.getString(CommonShared.LOGIN_TOKEN,""))
                .url(webUrl + method)
                .build();
        requestHttp(request, method, callback);
    }

    /**
     * 参数组装
     * @param context
     * @param method
     * @param params
     * @param isLogin  是否需要登录，传递userId
     * @param isDialog
     * @return
     */
    private RequestBody paramsBuilder(Context context,
                                      final String method,
                                      Map<String, Object> params,  //附加信息
                                      boolean isLogin,    //是否需要传递userId
                                      boolean isDialog  //是否显示弹出框
    ) {
        appContext = context;
        if (params == null) {
            params = new HashMap<String, Object>();
        }

        if (isDialog) {
            showLoadingDialog(context);
        }

        Gson gson = new Gson();
        LogUtil.showW("▶▶ " + method + " ▶ " + gson.toJson(params));
     //   System.out.println("▶▶ " + method + " ▶ " + gson.toJson(params));
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), gson.toJson(params));
        return requestBody;
    }

    /**
     * 发送请求
     * @param request
     * @param method
     * @param callback
     */
    private void requestHttp(Request request, final String method, final WebCallBack callback) {
        try {
            mOkHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    failedCallBack("您的网络走神了", callback);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String result = response.body().string();

                        LogUtil.showW("★★ " + method + " ★ " + result);
                        successCallBack(result, callback);
                    } else {
                        failedCallBack("服务器错误", callback);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 为HttpGet 的 url 方便的添加多个name value 参数。
     *
     * @param url
     * @param params
     * @return
     */
    public static String attachHttpGetParams(String url, Map<String, Object> params) {

        Iterator<String> keys = params.keySet().iterator();
        Iterator<Object> values = params.values().iterator();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("?");

        for (int i = 0; i < params.size(); i++) {
            String value = null;
            try {
                value = URLEncoder.encode(ToolUtil.changeString(values.next()), "utf-8");
            } catch (Exception e) {
                e.printStackTrace();
            }

            stringBuffer.append(keys.next() + "=" + value);
            if (i != params.size() - 1) {
                stringBuffer.append("&");
            }
            LogUtil.showW("stringBuffer  " + stringBuffer.toString());
        }

        return url + stringBuffer.toString();
    }

    /**
     * 显示进度条对话框
     */
    private int dialogShow = 0;
    private LoadingDialog mLoadingDialog = null;

    public void showLoadingDialog(Context context) {
        dialogShow++;
        if (mLoadingDialog == null) {
            mLoadingDialog = DialogUtil.showProgrssDialog(context);
            mLoadingDialog.setMessage("数据加载中...");
            mLoadingDialog.setCancelable(false);
            //    mLoadingDialog.setTitleText("数据加载中...");
        }
        mLoadingDialog.show();
    }

    public void dismissLoadingDialog() {
        try {
            if (dialogShow <= 1) {
                dialogShow = 0;
                if (mLoadingDialog.isShowing()) {
                    mLoadingDialog.dismiss();
                }
                mLoadingDialog = null;
            } else {
                dialogShow--;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface WebCallBack<T> {
        /**
         * 响应成功
         */
        void onWebSuccess(JSONObject resultJson);

        /**
         * 响应失败
         */
        void onWebFailed(String errorMsg);
    }

    /**
     * 统一同意处理成功信息
     *
     * @param result
     * @param callBack
     */
    private void successCallBack(final String result, final WebCallBack callBack) {
        okHttpHandler.post(new Runnable() {
            @Override
            public void run() {
                              dismissLoadingDialog();
                if (callBack != null) {
                    try {
                        Gson gson = new Gson();
                        JSONObject resultJson = new JSONObject(result);
                     //   System.out.println(result);
                     //   LogUtil.showW(result);
                        if (!(resultJson.optInt("code") == 0)) {
                            LogUtil.showW("查询失败");
                            ToastUtil.ToastMessage(appContext, resultJson.optString("msg"), ToastUtil.WRONG);
                            callBack.onWebFailed(result);
                        } else if (resultJson.optInt("code") == 0) {
                            callBack.onWebSuccess(resultJson);
                        }
                    } catch (Exception e) {
                        callBack.onWebFailed(result);
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    /**
     * 统一处理失败信息
     *
     * @param errorMsg
     * @param callBack
     */
    private void failedCallBack(final String errorMsg, final WebCallBack callBack) {
        okHttpHandler.post(new Runnable() {
            @Override
            public void run() {
                dismissLoadingDialog();
                //     LogUtil.webShow(errorMsg);
                if (callBack != null) {
                    callBack.onWebFailed(errorMsg);
                }
            }
        });
    }

    /**
     * 上传图片
     * @param context
     * @param file
     * @param callback
     */
    public void sendParamsPhotoFile(Context context, File file, final WebCallBack callback){
        final String method = URLConstant.UP_LOAD_PICTURE;
        try {
            LogUtil.showW(webUrl + method);
            RequestBody fileBody = RequestBody.create(MediaType.parse("image/jpg"), file);
            showLoadingDialog(context);
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("name", file.getName(), fileBody)
                    .build();
            final Request request = new Request.Builder()
                    .addHeader("token", CommonShared.getString(CommonShared.LOGIN_TOKEN, ""))
                    .post(requestBody)
                    .url(webUrl + method)
                    .build();
            mOkHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    failedCallBack("网络异常", callback);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String result = response.body().string();
                        LogUtil.showW("★★ " + method +" ★  " + result);

                        successCallBack(result, callback);
                    } else {
                        LogUtil.e("服务器错误",response.body().string());
                        failedCallBack("服务器错误", callback);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
