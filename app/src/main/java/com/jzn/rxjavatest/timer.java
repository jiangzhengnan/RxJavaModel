package com.jzn.rxjavatest;

import android.app.Activity;
import android.os.Bundle;

import com.jzn.rxjavatest.utils.MyLog;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;

/**
 * Timer
 *  做一些延时操作
 *  比如两秒后输入”啪啪啪“
 * Created by jiangzn on 16/9/9.
 */
public class timer extends Activity{
    String tag="xiaojingyu";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Observable.timer(2, TimeUnit.SECONDS)
                .subscribe(new Observer<Long>() {
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
                        MyLog.d("啪啪啪!");
                    }
                });
    }

}
