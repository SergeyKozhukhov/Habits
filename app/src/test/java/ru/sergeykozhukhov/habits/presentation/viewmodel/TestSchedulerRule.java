package ru.sergeykozhukhov.habits.presentation.viewmodel;

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
public class TestSchedulerRule implements TestRule {

    //private final TestScheduler testScheduler = new TestScheduler();
    private final Scheduler testScheduler = Schedulers.trampoline();

    public Scheduler getTestScheduler() {
        return testScheduler;
    }


    @Override
    public Statement apply(Statement base, Description description) {

        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                RxJavaPlugins.setIoSchedulerHandler(scheduler -> testScheduler);
                RxJavaPlugins.setComputationSchedulerHandler(scheduler -> testScheduler);
                RxJavaPlugins.setNewThreadSchedulerHandler(scheduler -> testScheduler);
                RxAndroidPlugins.setInitMainThreadSchedulerHandler(schedulerCallable -> testScheduler);

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
