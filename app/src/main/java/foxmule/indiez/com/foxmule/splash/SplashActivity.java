package foxmule.indiez.com.foxmule.splash;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import foxmule.indiez.com.foxmule.BaseActivity;
import foxmule.indiez.com.foxmule.BaseUI;
import foxmule.indiez.com.foxmule.R;
import foxmule.indiez.com.foxmule.utils.PermissionUtils;

public class SplashActivity extends BaseActivity implements SplashContract.View, BaseUI{

    private SplashPresenter presenter;
    private final int REQUEST_CODE_ASK_PERMISSIONS = 0;

    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void init() {
        initPresenter();
        initView();

    }

    private void initFbTrackers() {
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {

            }
        };

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
             //   nextActivity(newProfile);
            }
        };
        accessTokenTracker.startTracking();
        profileTracker.startTracking();

    }

    private void initAccessTokenTracking() {
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {

            }
        };
        accessTokenTracker.startTracking();
    }

    private void initProfileTracking() {
        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
                //   nextActivity(newProfile);
            }
        };
        profileTracker.startTracking();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initPresenter() {
        presenter = new SplashPresenter();
        presenter.attachView(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (haveRequiredAudioAndNetworkPermission()) {
        } else {
            List<String> permissionList = new ArrayList<String>();
            permissionList.add(Manifest.permission.RECORD_AUDIO);
            permissionList.add(Manifest.permission.ACCESS_NETWORK_STATE);
            PermissionUtils.askForPermissions(REQUEST_CODE_ASK_PERMISSIONS, this, permissionList);
        }
        presenter.isLoggedIn();


    }

    private boolean haveRequiredAudioAndNetworkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            return (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED)
                    && (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
        presenter.rxUnsubscribe();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS: {
                Map<String, Integer> perms = new HashMap<String, Integer>();
                perms.put(Manifest.permission.RECORD_AUDIO, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.ACCESS_NETWORK_STATE, PackageManager.PERMISSION_GRANTED);
                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);


                if (perms.get(Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED) {
                    // All Permissions Granted

                } else {
                    // Permission Denied
                    //TODO ask what to do when permissions are denied
                    finish();
                }
                break;
            }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        accessTokenTracker.stopTracking();
        profileTracker.stopTracking();

    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    @Override
    public void startTrackingTheProfileAndAccessToken() {
       AccessToken accessToken =  AccessToken.getCurrentAccessToken();
        if (accessToken.isExpired()) {
            initAccessTokenTracking();
        }

        if (Profile.getCurrentProfile() == null) {
            initProfileTracking();
        } else {

        }
    }

    @Override
    public void moveToHomeActivty() {

    }
}
