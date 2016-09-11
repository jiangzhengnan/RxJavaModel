package com.jzn.rxjavatest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;

/**
 * 基本的观察者，被观察者创建方式以及实现订阅。
 */
public class MainActivity extends AppCompatActivity {
    String tag = "MainActivity";

    //定义观察者
    Observer<String> observer = new Observer<String>() {
        @Override
        public void onCompleted() {
            Log.d(tag,"Completed！");
        }

        @Override
        public void onError(Throwable e) {
            Log.d(tag,"Error!");
        }

        @Override
        public void onNext(String s) {
            Log.d(tag,"Item: " + s);
        }
    };

    //实现了Observer的抽象类，对其进行了一些扩展，基本使用方式完全一样。
    Subscriber<String> subscriber = new Subscriber<String>() {

        @Override
        public void onCompleted() {
            Log.d(tag,"Completed！");
        }

        @Override
        public void onError(Throwable e) {
            Log.d(tag,"Error!");
        }

        @Override
        public void onNext(String s) {
            Log.d(tag,"Item: " + s);
        }
    };

    //被观察者  使用onCreate创建
    Observable observable = Observable.create(new Observable.OnSubscribe<String>(){
        @Override
        public void call(Subscriber<? super String> subscriber) {
            subscriber.onNext("Hello");
            subscriber.onNext("whale");
            subscriber.onNext("nangua");
            subscriber.onCompleted();
        }
    });

    //被观察者 使用just创建
    Observable observable1 = Observable.just("Hello","Hi","Aloha");

    //被观察者 使用数组传入创建
    String[] words = {"Hello","Hi","Aloha"};
    Observable observable2 = Observable.from(words);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //订阅
        observable.subscribe(subscriber);
        //observable.subscribe(observer);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
