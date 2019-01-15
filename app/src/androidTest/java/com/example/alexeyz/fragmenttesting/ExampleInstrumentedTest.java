package com.example.alexeyz.fragmenttesting;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.alexeyz.fragmenttesting.ui.main.MainFragment;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Rule
    public final ActivityTestRule<MainActivity> myActivityTestRule = new ActivityTestRule<>(MainActivity.class, false, true);

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();


        //Intent intent = new Intent(appContext, MainActivity.class);
        //appContext.startActivity(intent);

        assertEquals("com.example.alexeyz.fragmenttesting", appContext.getPackageName());
    }

    @Test
    public void checkFragment() {
        MainFragment fragment = new MainFragment();
        myActivityTestRule.getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();

        onView(withId(R.id.message)).check(matches(withText("MainFragment")));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.message)).check(matches(withText("1Changed")));
    }
}
