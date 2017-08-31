package foxmule.indiez.com.foxmule.onBoarding.enterPhoneNumber;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import foxmule.indiez.com.foxmule.BaseActivity;
import foxmule.indiez.com.foxmule.BaseUI;
import foxmule.indiez.com.foxmule.R;
import foxmule.indiez.com.foxmule.domain.OtpResponse;
import foxmule.indiez.com.foxmule.onBoarding.enterConfirmationCode.EnterConfirmationCode;

/**
 * Created by ayushbagaria on 8/30/17.
 */

public class EnterPhoneNumberActivity extends BaseActivity implements BaseUI, EnterPhoneNumberContract.View {

    @InjectView(R.id.et_phone_number)
    EditText etPhoneNumber;


    @InjectView(R.id.tv_error)
    TextView tvError;

    @InjectView(R.id.btn_next)
    Button btnNext;

    @InjectView(R.id.tv_login)
    TextView btnLogin;
    private EnterPhoneNumberPresenter presenter;


    public static void start(Activity activity) {
        Intent intent = new Intent(activity, EnterPhoneNumberActivity.class);
        activity.startActivity(intent);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_phone_number);
        ButterKnife.inject(this);
        init();

    }


    @Override
    public void init() {
        initView();
        initPresenter();
    }

    @Override
    public void initView() {
        etPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvError.setVisibility(View.GONE);
                if (s.length() >= 4) {
                    btnNext.setEnabled(true);
                } else {
                    btnNext.setEnabled(false);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public void initPresenter() {
        this.presenter = new EnterPhoneNumberPresenter();
        this.presenter.attachView(this);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @OnClick(R.id.btn_next)
    public void validateNumberAndGetConfirmationCode () {
        presenter.validateNumberAndGenerateCode(etPhoneNumber.getText().toString());
    }

    @Override
    public void showInvalidePhoneNumberError() {
        tvError.setVisibility(View.VISIBLE);
    }

    @Override
    public void goToLoginView() {

    }

    @Override
    public void moveToOtpScreen(OtpResponse otpResponse) {
        EnterConfirmationCode.start(this, etPhoneNumber.getText().toString(), otpResponse.getOtp() );
    }

    @OnClick(R.id.tv_login)
    public void alreasyHasAnAccount() {
        presenter.userAlreadyHasAnAccount();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
        presenter.rxUnsubscribe();
    }
}
