package com.example.alexeyz.fragmenttesting;


import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.action.ViewActions.typeTextIntoFocusedView;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;

@LargeTest
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.JVM)
public class LoginTest {

    public static final String RIGHT_USERNAME = "1";
    public static final String RIGHT_PASSWORD = "1";
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    private ViewInteraction etUsername;
    private ViewInteraction etPassword;
    private ViewInteraction bLogin;
    private ViewInteraction tvError;

    @Before
    public void setUp() {
        etUsername = onView(withId(R.id.et_name));
        etPassword = onView(withId(R.id.et_password));

        bLogin = onView(withId(R.id.b_login));
        tvError = onView(withId(R.id.tv_error));
    }

    @Test
    public void loginButtonEnableDisableTest() {
        bLogin.check(matches(not(isEnabled())));

        etUsername.perform(click(), typeText("bla bla bla"));

        etPassword.perform(click(), typeText("bla bla bla"));

        bLogin.check(matches(isEnabled()));

        etUsername.perform(click(), clearText());

        bLogin.check(matches(not(isEnabled())));

        etPassword.perform(click(), clearText());

        bLogin.check(matches(not(isEnabled())));

    }

    @Test
    public void loginError() {
        bLogin.check(matches(not(isEnabled())));

        etUsername.perform(click(), typeText("wrong"));
        etPassword.perform(click(), typeText("wrong"));

        bLogin.perform(click());

        waitFor(500);

        tvError.check(matches(isDisplayed()));

        waitFor(2500);

        tvError.check(matches(not(isDisplayed())));
    }

    @Test
    public void loginSuccess() {
        etUsername.perform(click(), typeText(RIGHT_USERNAME));

        etPassword.perform(click(), typeText(RIGHT_PASSWORD));

        bLogin.perform(click());

        waitFor(1000);

        etPassword.check(matches(not(isDisplayed())));
        etUsername.check(matches(not(isDisplayed())));

        waitFor(1000);

        onView(withText("Hello")).check(matches(isDisplayed()));
    }

    private void waitFor(int msec) {
        try {
            Thread.sleep(msec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
