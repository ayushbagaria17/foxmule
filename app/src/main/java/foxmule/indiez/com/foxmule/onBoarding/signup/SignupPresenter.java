package foxmule.indiez.com.foxmule.onBoarding.signup;

import java.util.zip.CheckedOutputStream;

import foxmule.indiez.com.foxmule.Constants;
import foxmule.indiez.com.foxmule.utils.SharedPreferencesUtil;

/**
 * Created by ayushbagaria on 8/28/17.
 */

public class SignupPresenter implements SignupContract.Presenter {


    private SignupContract.View view;
    private SharedPreferencesUtil preferences;

    @Override
    public void attachView(SignupContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {

    }

    @Override
    public void rxUnsubscribe() {

    }

    @Override
    public void userLoggedInUsingFb() {
        if (view != null) {
            preferences = SharedPreferencesUtil.getInstance(view.getContext());
            preferences.saveData(Constants.KEY.IS_LOGGED_IN, true);
            preferences.saveData(Constants.KEY.LOG_IN_MODE, Constants.VALUE.MODE_FB);
            view.moveToHomeActivity();
        }
    }
}
