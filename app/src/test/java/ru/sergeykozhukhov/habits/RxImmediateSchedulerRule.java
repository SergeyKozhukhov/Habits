package ru.sergeykozhukhov.habits;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;


// https://www.infoq.com/articles/Testing-RxJava2/
// https://medium.com/@boonkeat/android-test-driven-design-using-mvvm-rxjava-and-livedata-d7a38dc25913
// https://github.com/SPHTech/TestDrivenMVVM/blob/master/app/src/test/java/sg/com/sph/testdrivenmvvm/RxImmediateSchedulerRule.kt
// https://www.aanandshekharroy.com/articles/2018-01/rxjava-schedulers
public class RxImmediateSchedulerRule implements TestRule {

    @Override
    public Statement apply(Statement base, Description description) {

        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                RxJavaPlugins.setIoSchedulerHandler(scheduler -> Schedulers.trampoline());
                RxJavaPlugins.setComputationSchedulerHandler(scheduler -> Schedulers.trampoline());
                RxJavaPlugins.setNewThreadSchedulerHandler(scheduler -> Schedulers.trampoline());
                RxAndroidPlugins.setInitMainThreadSchedulerHandler(schedulerCallable -> Schedulers.trampoline());

                try {
                    base.evaluate();
                } finally {
                    RxJavaPlugins.reset();
                    RxAndroidPlugins.reset();
                }
            }
        };
    }
}
