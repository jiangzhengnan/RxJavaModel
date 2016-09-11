package com.jzn.rxjavatest;

import android.app.Activity;
import android.os.Bundle;

import com.jzn.rxjavatest.utils.MyLog;

import rx.Observable;
import rx.functions.Action1;

/**
 * 数组，list遍历
 * Created by jiangzn on 16/9/9.
 */
public class foreach extends Activity {
    String[] names = {"Tom","Lily","Fucker","Bill"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Observable.from(names)
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        MyLog.d(s);
                    }
                });
    }

}
