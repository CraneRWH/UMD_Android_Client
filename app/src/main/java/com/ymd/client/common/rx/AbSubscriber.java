package com.ymd.client.common.rx;

import com.ymd.client.common.net.exception.ApiException;
import com.ymd.client.common.net.exception.ExceptionHandle;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * @author zhl
 * @class com.wealth.appclient.common.rx
 * @time 2018/8/2 0002 13:55
 * @description
 */
public abstract class AbSubscriber<T> implements Subscriber<T> {

    @Override
    public void onSubscribe(Subscription s) {
        s.request(1);
    }

    @Override
    public void onNext(T t) {
        OnSuccess(t);
    }

    @Override
    public void onError(Throwable t) {
        OnFail(ExceptionHandle.handleException(t));
    }

    @Override
    public void onComplete() {
        OnCompleted();
    }

    public abstract void OnSuccess(T t);

    public abstract void OnFail(ApiException e);

    public abstract void OnCompleted();
}
