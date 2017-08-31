package foxmule.indiez.com.foxmule.onBoarding.signup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import foxmule.indiez.com.foxmule.BaseActivity;
import foxmule.indiez.com.foxmule.BaseUI;
import foxmule.indiez.com.foxmule.Constants;
import foxmule.indiez.com.foxmule.Logger;
import foxmule.indiez.com.foxmule.R;
import foxmule.indiez.com.foxmule.onBoarding.enterPhoneNumber.EnterPhoneNumberActivity;

/**
 * Created by ayushbagaria on 8/28/17.
 */

public class SignupActivity extends BaseActivity implements SignupContract.View, BaseUI {

    @InjectView(R.id.fb_login_btn)
    LoginButton fbSignUp;

    @InjectView(R.id.tv_signup_phoneNo)
    TextView tvPhoneSingUp;

    @InjectView(R.id.tv_login)
    TextView tvLogin;
    private SignupPresenter presenter;
    private CallbackManager mFacebookCallbackManager;
    private AccessTokenTracker mFacebookAccessTokenTracker;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.inject(this);
        init();
    }

    @Override
    public void init() {
        initPresenter();
        initView();
    }

    @Override
    public void initView() {
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        mFacebookCallbackManager = CallbackManager.Factory.create();
        fbSignUp.setReadPermissions(Arrays.asList("public_profile","user_friends","email"));
        fbSignUp.registerCallback(mFacebookCallbackManager, new FacebookCallback<LoginResult>() {
            private ProfileTracker mProfileTracker;
            @Override
            public void onSuccess(LoginResult loginResult) {
                Logger.logError(loginResult.toString());
                AccessToken accessToken = loginResult.getAccessToken();
                Profile profile = Profile.getCurrentProfile();
                if(Profile.getCurrentProfile() == null) {
                    mProfileTracker = new ProfileTracker() {
                        @Override
                        protected void onCurrentProfileChanged(Profile profile, Profile profile2) {
                            Logger.logInfo(profile2.getFirstName());
                            mProfileTracker.stopTracking();
                        }
                    };
                    // no need to call startTracking() on mProfileTracker
                    // because it is called by its constructor, internally.
                } else {
                    Logger.logInfo(profile.getFirstName());
                }
                presenter.userLoggedInUsingFb();
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                Logger.logError(exception.toString());
            }
        });

    }

    @Override
    public void initPresenter() {
        presenter = new SignupPresenter();
        presenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
        presenter.rxUnsubscribe();
    }

    @Override
    public Context getContext() {
        return this;
    }


    @OnClick(R.id.tv_signup_phoneNo)
    public void signupUsingPhone() {
        EnterPhoneNumberActivity.start(this);
    }


    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
        super.onActivityResult(requestCode, responseCode, intent);
        //Facebook login
        mFacebookCallbackManager.onActivityResult(requestCode, responseCode, intent);
    }

    @Override
    public void moveToHomeActivity() {

    }
}
