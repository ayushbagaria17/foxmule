package foxmule.indiez.com.foxmule.onBoarding.enterConfirmationCode;

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
import foxmule.indiez.com.foxmule.Logger;
import foxmule.indiez.com.foxmule.R;
import foxmule.indiez.com.foxmule.domain.OtpResponse;
import foxmule.indiez.com.foxmule.onBoarding.enterPhoneNumber.EnterPhoneNumberActivity;
import foxmule.indiez.com.foxmule.onBoarding.enterPhoneNumber.EnterPhoneNumberPresenter;

/**
 * Created by ayushbagaria on 8/31/17.
 */

public class EnterConfirmationCode extends BaseActivity implements BaseUI, EnterConfirmationCodeContract.View {


    private static final String PHONE_NUMBER = "phoneNumber";
    private static final String OTP = "otp";
    @InjectView(R.id.et_confirmation_code)
    EditText etConfirmationCode;

    @InjectView(R.id.tv_code_sent_to_number)
    TextView tvCodeSentToNumber;


    @InjectView(R.id.tv_error)
    TextView tvError;

    @InjectView(R.id.btn_next)
    Button btnNext;

    @InjectView(R.id.tv_login)
    TextView btnLogin;
    private EnterConfirmationCodePresenter presenter;
    private String code;
    private String number;


    public static void start(Activity activity, String phoneNumber, String otp) {
        Intent intent = new Intent(activity, EnterConfirmationCode.class);
        intent.putExtra(PHONE_NUMBER, phoneNumber);
        intent.putExtra(OTP, otp);
        activity.startActivity(intent);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_confirmation);
        ButterKnife.inject(this);
        init();
        code = getIntent().getStringExtra(OTP);
        number = getIntent().getStringExtra(PHONE_NUMBER);
        presenter.setNumberAndCode(code, number);

    }


    @Override
    public void init() {
        initView();
        initPresenter();
    }

    @Override
    public void initView() {
        tvCodeSentToNumber.setText(getResources().getString(R.string.label_enter_code_sent_to,number));
        etConfirmationCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvError.setVisibility(View.GONE);
                if (s.length() == 4) {
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
        this.presenter = new EnterConfirmationCodePresenter();
        this.presenter.attachView(this);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @OnClick(R.id.btn_next)
    public void checkConfirmationCode () {
        presenter.validateCode(etConfirmationCode.getText().toString());
    }

    @Override
    public void showInvalideConfirmationCodeError() {
        tvError.setVisibility(View.VISIBLE);
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

    @Override
    public void moveToEnterUsernameView(String code, String number) {
        Logger.logInfo(code);
    }

    @Override
    public void moveToLoginView() {

    }
}
