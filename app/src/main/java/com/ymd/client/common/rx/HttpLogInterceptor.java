package com.ymd.client.common.rx;

import com.socks.library.KLog;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import okio.Buffer;
import okio.BufferedSource;

/**
 * @author zhl
 * @time 2018/6/28 0028 10:29
 * @description
 */
public class HttpLogInterceptor implements Interceptor {
    private static final Charset UTF8 = Charset.forName("UTF-8");

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        Response response;
        try {
            response = chain.proceed(request);
        } catch (Exception e) {
            throw e;
        }

        ResponseBody responseBody = response.body();
        if (!HttpHeaders.hasBody(response) || bodyEncoded(response.headers())) {
            KLog.d("<-- END HTTP");
        } else {
            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();

            long contentLength = responseBody.contentLength();
            Charset charset = UTF8;

            KLog.d("the request url is : "+response.request().url());

            if (contentLength != 0) {
                KLog.d("the response content is : "+buffer.clone().readString(charset));
            }
        }
        return response;
    }

    private boolean bodyEncoded(Headers headers) {
        String contentEncoding = headers.get("Content-Encoding");
        return contentEncoding != null && !contentEncoding.equalsIgnoreCase("identity");
    }
}
