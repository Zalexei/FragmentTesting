package com.example.alexeyz.fragmenttesting.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.transition.AutoTransition;
import android.transition.ChangeBounds;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alexeyz.fragmenttesting.DataFragment;
import com.example.alexeyz.fragmenttesting.Location;
import com.example.alexeyz.fragmenttesting.R;
import com.example.alexeyz.fragmenttesting.SelinaService;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxCompoundButton;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class MainFragment extends Fragment {

    private MainViewModel mViewModel;
    private View view;

    TextView tvError;
    Button bLogin;
    private TextView etUsername;
    private TextView etPassword;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main_fragment, container, false);

        tvError = view.findViewById(R.id.tv_error);

        bLogin = view.findViewById(R.id.b_login);

        etUsername = view.findViewById(R.id.et_name);
        etPassword = view.findViewById(R.id.et_password);

//        RxTextView.textChangeEvents(etUsername).subscribe(
//                val -> Toast.makeText(getContext(), val.text(), Toast.LENGTH_SHORT).show()
//        );
//
//        RxTextView.textChangeEvents(etPassword).subscribe(
//                val -> Toast.makeText(getContext(), val.text(), Toast.LENGTH_SHORT).show()
//        );



        //SelinaService.getInstance().updateLocations().observeOn(AndroidSchedulers.mainThread()).subscribe(this::showList, this::showError);

        return view;
    }

    private void showError(Throwable throwable) {
        Toast.makeText(this.getContext(), "Error r: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }

    private void showList(List<Location> locations) {
        Toast.makeText(this.getContext(), "List received, size: " + locations.size(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        mViewModel.state.observeOn(AndroidSchedulers.mainThread()).subscribe(this::render);
        mViewModel.message.observeOn(AndroidSchedulers.mainThread()).subscribe(this::message);

        Observable.combineLatest(
                RxTextView.textChangeEvents(etUsername),
                RxTextView.textChangeEvents(etPassword),
                (name, password) -> mViewModel.checkFields(name.text().toString(), password.text().toString())
        ).subscribe(
                val -> {},
                err -> {}
        );

        RxView.clicks(bLogin).subscribe(v -> mViewModel.login(etUsername.getText().toString(), etPassword.getText().toString()));
    }

    private void message(LoginMessage loginMessage) {
        switch (loginMessage.messageType) {
            case ERROR:
                tvError.setVisibility(View.VISIBLE);
                tvError.setText(loginMessage.message);
                break;
            case REMOVE_ERROR:
                tvError.setVisibility(View.GONE);
                tvError.setText(null);
                break;
            case ENABLE_BUTTON:
                bLogin.setEnabled(true);
                break;
            case DISABLE_BUTTON:
                bLogin.setEnabled(false);
                break;
            case CLEAR_FIELDS:
                break;
        }

    }

    private void render(LoginState loginState) {
        switch (loginState.state) {
            case LOADING:
                bLogin.setText("Loading");
                break;
            case EMPTY_FIELDS:
                bLogin.setEnabled(false);
                break;
            case READY_TO_LOGIN:
                bLogin.setEnabled(true);
                break;
            case SWITCH_TO_NEXT:
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, new DataFragment())
                        .commitNow();
                break;
            case LOGGED_IN:
                bLogin.setEnabled(false);
                bLogin.setText("Logged");

                TransitionManager.beginDelayedTransition(view.findViewById(R.id.main), new AutoTransition());
                etUsername.setVisibility(View.GONE);
                etPassword.setVisibility(View.GONE);

                break;
        }
    }

}
