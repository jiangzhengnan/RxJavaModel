package com.jzn.rxjavatest;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.jzn.rxjavatest.utils.MyLog;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * 变换
 * 就是将事件序列中的对象或整个序列进行加工处理，转换成不同的事件或事件序列
 * 简而言之就是参数类型和返回类型不同
 * 有两种，一种是map()，一种是flatMap()
 * 下面分别用两个小栗子举例说明之
 * Created by jiangzn on 16/9/9.
 */
public class map_flatmap extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //mapExample();
        flatMapExample();
    }


    class Student{
        String name;

        public Student(String name) {
            this.name = name;
        }

        Course[] courses;

        public Course[] getCourses() {
            return courses;
        }

        public void setCourses(Course[] course) {
            this.courses = course;
        }
    }
    class Course
    {
        public Course(String name) {
            this.name = name;
        }

        String name;
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
    }
    //使用flatMap()的实例
    // 打印出每个学生所需要修的所有课程的名称
    //（每个学生只有一个名字，但却有多个课程。）
    private void flatMapExample() {
        Student student1 = new Student("小明");
        Student student2 = new Student("小红");
        Course[] courses1 = {new Course("语文"),new Course("数学"),new Course("英语")};
        Course[] courses2 = {new Course("物理"),new Course("化学"),new Course("生物")};
        student1.setCourses(courses1);
        student2.setCourses(courses2);
        Student[] students = {student1,student2};
        Subscriber<Course> subscriber = new Subscriber<Course>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(Course course) {
                MyLog.d(course.getName());
            }
        };

        Observable.from(students)
                .flatMap(new Func1<Student, Observable<Course>>() {
                    @Override
                    public Observable<Course> call(Student student) {
                        MyLog.d(student.name);
                        return Observable.from(student.getCourses());
                        //flatMap() 和 map() 有一个相同点：它也是把传入的参数转化之后返回另一个对象。
                        // 但需要注意，和 map() 不同的是， flatMap() 中返回的是个 Observable 对象，
                        // 并且这个 Observable 对象并不是被直接发送到了 Subscriber 的回调方法中。
                        // flatMap() 的原理是这样的：1. 使用传入的事件对象创建一个 Observable 对象；
                        // 2. 并不发送这个 Observable, 而是将它激活，于是它开始发送事件；
                        // 3. 每一个创建出来的 Observable 发送的事件，都被汇入同一个 Observable ，
                        // 而这个 Observable 负责将这些事件统一交给 Subscriber 的回调方法。这三个步骤，
                        // 把事件拆成了两级，通过一组新创建的 Observable 将初始的对象『铺平』
                        // 之后通过统一路径分发了下去。而这个『铺平』就是 flatMap() 所谓的 flat。
                    }
                }).subscribe(subscriber);
    }



    //使用map()的实例
    private void mapExample() {
        Observable.just("images/logo.png")  //输入类型
        .map(new Func1<String, Bitmap>() {
            @Override
            public Bitmap call(String filepath) {
                return getBitmapFromPath(filepath);
            }
        })
        .subscribe(new Action1<Bitmap>() {
            @Override
            public void call(Bitmap bitmap) {
                showBitmap(bitmap);
            }
        });

        //这里出现了一个叫做 Func1 的类。它和 Action1 非常相似，
        //也是 RxJava 的一个接口，用于包装含有一个参数的方法。
        // Func1 和 Action 的区别在于， Func1 包装的是有返回值的方法。
        // 另外，和 ActionX 一样， FuncX 也有多个，用于不同参数个数的方法。
        // FuncX 和 ActionX 的区别在 FuncX 包装的是有返回值的方法。
    }
    //模拟方法显示图片
    private void showBitmap(Bitmap bitmap) {
        MyLog.d("显示图片辣");
    }
    //模拟方法得到图片地址
    private Bitmap getBitmapFromPath(String filepath) {
        MyLog.d("从地址得到图片辣");
        return null;
    }



}
