package com.jzn.rxjavatest;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 两个小栗子
 * a打印字符串数组
 * b由id取得突破并显示  设置执行的线程
 * Created by jiangzn on 16/9/8.
 */
public class twoexample extends Activity {
    String tag = "xiaojingyu";
    String[] names = {"a","b","c"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test2);
        Log.d(tag,"MainActivity");
        // a();
         b();
    }

    private void b() {
        final int drawableRes = R.drawable.aa;
        final ImageView imageView = (ImageView) findViewById(R.id.iv_test2);
        Observable.create(new Observable.OnSubscribe<Drawable>() {
            @Override
            public void call(Subscriber<? super Drawable> subscriber) {
                Log.d(tag,"call:" + Thread.currentThread().getName());
                //打印结果：call:RxCachedThreadScheduler-1...
                Drawable drawable = getResources().getDrawable(drawableRes);
                subscriber.onNext(drawable);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io()) //指定OnSubscribe被激活时处在的线程，事件产生线程
                .observeOn(AndroidSchedulers.mainThread())  //Subscriber所运行的线程，事件消费的线程
                .subscribe(new Subscriber<Drawable>() {
            @Override
            public void onCompleted() {
                Log.d(tag,"Completed!");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(tag,e.getMessage());
            }

            @Override
            public void onNext(Drawable drawable) {
                imageView.setImageDrawable(drawable);
                Log.d(tag,"加载线程：" + Thread.currentThread().getName());
                //打印结果：加载线程：main
            }
        });
    }

    private void a() {
        Observable.from(names)
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.d(tag,s);
                    }
                });
    }


}
