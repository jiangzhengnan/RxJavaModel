package com.jzn.rxjavatest;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.ViewTreeObserver;

import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * subscribe()支持的不完整回调
 * Created by jiangzn on 16/9/8.
 */
public class simplestAty extends Activity {
    String tag = "xiaojingyu";

    Action1<String> onNextAction = new Action1<String>() {
        @Override
        public void call(String s) {
            Log.d(tag,s);
        }
    };

    Action1<Throwable> onErrorAction = new Action1<Throwable>() {
        @Override
        public void call(Throwable throwable) {
            //错误处理
        }
    };

    Action0 onCompleteAction = new Action0() {
        @Override
        public void call() {
            Log.d(tag,"completed");
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        Observable observable = Observable.just("hello");
        observable.subscribe(onNextAction,onErrorAction,onCompleteAction);
    }


}
