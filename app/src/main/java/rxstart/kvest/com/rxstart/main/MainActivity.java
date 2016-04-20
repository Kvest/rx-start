package rxstart.kvest.com.rxstart.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;


import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rxstart.kvest.com.rxstart.R;

/**
 * Created by roman on 4/20/16.
 */
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        findViewById(R.id.test1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test1();
            }
        });
        findViewById(R.id.test2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test2();
            }
        });
        findViewById(R.id.test3).setOnClickListener(v->test3());
        findViewById(R.id.test4).setOnClickListener(v->test4());
    }

    private void test1() {
        Observable<String> myObservable = Observable.create(
                new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> sub) {
                        sub.onNext("Hello, world!");
                        sub.onCompleted();
                    }
                }
        );

        Subscriber<String> mySubscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Toast.makeText(MainActivity.this, "onCompleted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(MainActivity.this, "onError", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(String s) {
                Toast.makeText(MainActivity.this, "onNext[" + s + "]", Toast.LENGTH_SHORT).show();
            }
        };

        myObservable.subscribe(mySubscriber);
    }

    private void test2() {
        Observable<String> observable = Observable.just("Test 2");
        Action1<String> onNextAction = new Action1<String>() {
            @Override
            public void call(String s) {
                Toast.makeText(MainActivity.this, "onNextAction[" + s + "]", Toast.LENGTH_SHORT).show();
            }
        };
        observable.subscribe(onNextAction);
    }

    private void test3() {
        Observable.just("Test3")
                .subscribe(s->Toast.makeText(MainActivity.this, "onNextAction[" + s + "]", Toast.LENGTH_SHORT).show());
    }

    private void test4() {
        Observable.just("test 4")
                .map(s->s + " salt")
                .map(s->s.hashCode())
                .map(i->"Hash is " + Integer.toHexString(i))
                .subscribe(s->Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show());
    }
}
