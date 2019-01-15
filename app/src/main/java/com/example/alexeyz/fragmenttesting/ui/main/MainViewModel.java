package com.example.alexeyz.fragmenttesting.ui.main;

import android.arch.lifecycle.ViewModel;

import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;

import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;

public class MainViewModel extends ViewModel {
    BehaviorSubject<LoginState> state;
    PublishSubject<LoginMessage> message;

    public MainViewModel() {
        state = BehaviorSubject.createDefault(new LoginState(LoginState.State.EMPTY_FIELDS));
        message = PublishSubject.create();
    }

    public void login(String login, String password) {
        if(login.equals("1") && password.equals("1")) {
            state.onNext(new LoginState(LoginState.State.LOGGED_IN));

            Completable.timer(2, TimeUnit.SECONDS).subscribe(
                    () -> state.onNext(new LoginState(LoginState.State.SWITCH_TO_NEXT))
            );
        } else {
            message.onNext(new LoginMessage(LoginMessage.MessageType.ERROR, "Wrong password"));
            Completable.timer(2, TimeUnit.SECONDS).subscribe(
                    () -> message.onNext(new LoginMessage(LoginMessage.MessageType.REMOVE_ERROR, ""))
            );

            message.onNext(new LoginMessage(LoginMessage.MessageType.CLEAR_FIELDS, ""));
        }
    }

    public boolean checkFields(String name, String password) {
        if(name.length() > 0 && password.length() > 0) {
            message.onNext(new LoginMessage(LoginMessage.MessageType.ENABLE_BUTTON, ""));
        } else {
            message.onNext(new LoginMessage(LoginMessage.MessageType.DISABLE_BUTTON, ""));
        }

        return true;
    }
}
