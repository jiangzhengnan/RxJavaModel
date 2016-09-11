package com.jzn.rxjavatest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Merge
 * 使用merge合并两个数据源
 * 例如一组数据来自网络，一组数据来自文件，需要合并数据并一起显示的情况
 *
 * 可以理解为拼接两个Observable的输出，不保证顺序，按照事件产生的顺序发送给订阅者
 * Created by jiangzn on 16/9/8.
 */
public class merge extends Activity {
    String tag = "xiaojingyu";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test3);
        Observable.merge(getDataFromFile()  ,getDataFromNet())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        Log.d(tag,"done loading all data");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(tag,"error");
                    }

                    @Override
                    public void onNext(String s) {
                        Log.d(tag,"merge:" + s);
                    }
                });
    }


    private Observable<String> getDataFromFile() {
        String[] strs = {"filedata1","filedata2","filedata3","filedata4"};
        Observable<String> temp = Observable.from(strs);
        return temp;
    }

    private Observable<String> getDataFromNet() {
        String[] strs = {"netdata1","netdata2","netdata3","netdata4"};
        Observable<String> temp = Observable.from(strs);
        return temp;
    }

}
