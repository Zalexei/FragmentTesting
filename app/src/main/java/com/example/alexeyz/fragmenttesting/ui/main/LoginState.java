package com.example.alexeyz.fragmenttesting.ui.main;

class LoginState {
    State state;

    public LoginState(State state) {

        this.state = state;
    }

    public State getState() {
        return state;
    }

    public enum State {
        EMPTY_FIELDS,
        READY_TO_LOGIN,
        LOADING,
        LOGGED_IN,
        SWITCH_TO_NEXT
    }
}
