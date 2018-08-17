package com.ymd.client.common.rx;

import android.text.TextUtils;

import com.ymd.client.common.net.exception.ResponseCode;
import com.ymd.client.model.bean.ResponseBean;

import org.reactivestreams.Publisher;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhl  on 2017/6/3 08:45 *
 * 作用: RxJava 的线程管理和返回结果统一处理的ObservableTransformer
 */
public class RxSchedulerHelper {

    public static <T> FlowableTransformer<T, T> io_main() {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 处理返回结果
     *
     * @param <T> 返回值
     * @return 结果统一处理的ObservableTransformer
     */
    public static <T> FlowableTransformer<T, T> handleResult() {
        return new FlowableTransformer<T, T>() {
            @Override
            public Flowable<T> apply(Flowable<T> upstream) {
                return upstream.flatMap(new Function<T, Flowable<T>>() {
                    @Override
                    public Flowable<T> apply(final T t) {
                        ResponseBean b = (ResponseBean) t;
                        if (b != null && !TextUtils.isEmpty(b.code) && b.code.equals(ResponseCode.SUCCESS)) {
                            return Flowable.create(new FlowableOnSubscribe<T>() {
                                @Override
                                public void subscribe(FlowableEmitter<T> emitter) {
                                    try {
                                        emitter.onNext(t);
                                        emitter.onComplete();
                                    } catch (Exception e) {
                                        emitter.onError(e);
                                    }
                                }
                            }, BackpressureStrategy.BUFFER);
                        } else {
                            String msg = ((ResponseBean) t).msg;
                            if (TextUtils.isEmpty(msg)) {
                                return Flowable.error(new Exception(ResponseCode.getInstance().initResponseCode().get(((ResponseBean) t).code)));
                            } else {
                                return Flowable.error(new Exception(msg));
                            }
                        }
                    }
                });
            }
        };
    }
}
