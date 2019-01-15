package com.example.alexeyz.fragmenttesting;

import com.example.alexeyz.fragmenttesting.ui.main.MainViewModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class ExampleUnitTest {
    @Mock
    private SelinaService selinaService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void checkRxJavaCall() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(new Function<Callable<Scheduler>, Scheduler>() {
            @Override
            public Scheduler apply(Callable<Scheduler> schedulerCallable) throws Exception {
                return Schedulers.trampoline();
            }
        });

        //testObserver =
        SelinaService.getInstance().updateLocations().observeOn(AndroidSchedulers.mainThread()).subscribe(this::show, this::error);

        List<Location> a = SelinaService.getInstance().updateLocations().blockingFirst();

        MainViewModel mainViewModel = new MainViewModel();

        //testObserver.awaitTerminalEvent();

        //testObserver.assertNoErrors().assertValue(l -> l.size() > 0);
    }

    private void error(Throwable throwable) {
        int a = 1;
    }

    private void show(List<Location> locations) {
        int b = locations.size();
    }
}