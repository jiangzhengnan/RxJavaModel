package com.jzn.rxjavatest;

import android.app.Activity;
import android.os.Bundle;

import com.jzn.rxjavatest.utils.MyLog;

import rx.Observable;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * 生命周期
 * 防止Observable持有Context导致的内存泄露
 * 解决方案就是在生命周期的某个时刻取消订阅。
 * 一个很常见的模式就是使用CompositeSubscription来持有所有的Subscriptions，
 * 然后在onDestroy()或者onDestroyView()里取消所有的订阅。
 *
 * Created by jiangzn on 16/9/11.
 */
public class lifecycle extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private CompositeSubscription mCompositeSubscription
            = new CompositeSubscription();

    private void doSomething() {
        mCompositeSubscription.add( Observable.just("Hello, World!")
                .subscribe(new Action1<String>(){
                    @Override
                    public void call(String s) {
                        MyLog.d(s);
                    }
                }));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompositeSubscription.unsubscribe();
        //注意! 一旦你调用了 CompositeSubscription.unsubscribe()，
        // 这个CompositeSubscription对象就不可用了, 如果你还想使用CompositeSubscription，
        // 就必须在创建一个新的对象了。
    }
}
