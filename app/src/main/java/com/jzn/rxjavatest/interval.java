package com.jzn.rxjavatest;

import android.app.Activity;
import android.os.Bundle;

import com.jzn.rxjavatest.utils.MyLog;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;

/**
 * interval
 * 做周期性操作
 * 每两秒输出"啪啪啪"
 * Created by jiangzn on 16/9/9.
 */
public class interval extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Observable.interval(2, TimeUnit.SECONDS)
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                        MyLog.d("completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        MyLog.d("error");
                    }

                    @Override
                    public void onNext(Long aLong) {
                        MyLog.d("啪啪啪");
                    }
                });
    }
}
