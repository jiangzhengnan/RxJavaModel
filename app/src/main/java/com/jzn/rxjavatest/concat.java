package com.jzn.rxjavatest;

import android.app.Activity;
import android.os.Bundle;

import com.jzn.rxjavatest.utils.MyLog;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * concat与operator
 * 使用模拟三级缓存实现来举例
 * 首先检查内存是否有缓存
 * 然后检查文件缓存
 * 最后才从网络中取
 * 只要满足前面任何一个条件，就不会再执行后面的操作
 * Created by jiangzn on 16/9/9.
 */
public class concat extends Activity {
    String memoryChache = null;    //假装是内存缓存
    String diskChache = "read to diskChache";      //假装是磁盘缓存

    Observable<String> memory = Observable.create(new Observable.OnSubscribe<String>() {
        @Override
        public void call(Subscriber<? super String> subscriber) {
            if (memoryChache != null) {
                subscriber.onNext(memoryChache);
            } else {
                subscriber.onCompleted();
            }
        }
    });

    Observable<String> disk = Observable.create(new Observable.OnSubscribe<String>() {
        @Override
        public void call(Subscriber<? super String> subscriber) {
            if (diskChache != null) {
                subscriber.onNext(diskChache);
            } else {
                subscriber.onCompleted();
            }
        }
    });

    Observable<String> network = Observable.just("network");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //主要靠concat operator来实现
        Observable.concat(memory,disk,network)
                .first()    //first过滤器：只执行满足条件的第一个观察者
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        MyLog.d("----subsribe:" + s);
                    }
                });

    }



}
