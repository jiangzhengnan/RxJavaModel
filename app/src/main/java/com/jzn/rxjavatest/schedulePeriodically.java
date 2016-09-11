package com.jzn.rxjavatest;

import android.app.Activity;
import android.os.Bundle;

import com.jzn.rxjavatest.utils.MyLog;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * schedulePeriodically
 * 轮询请求
 * Created by jiangzn on 16/9/9.
 */
public class schedulePeriodically extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Observable.create(new Observable.OnSubscribe<String>(
        ) {
            @Override
            public    void call(final Subscriber<? super String> subscriber) {
                    Schedulers.io().createWorker()  //指定在io线程执行
                            .schedulePeriodically(new Action0() {
                                @Override
                                public void call() {
                                    subscriber.onNext("doNetworkCallAndGetStringResult");
                                }
                            }, 2000, 1000, TimeUnit.MILLISECONDS);//初始延迟，polling延迟
                }

        })
                .subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                MyLog.d("polling..." + s);
            }
        });

    }

}
