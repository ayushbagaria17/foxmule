package foxmule.indiez.com.foxmule.splash;

import foxmule.indiez.com.foxmule.Constants;
import foxmule.indiez.com.foxmule.utils.SharedPreferencesUtil;

/**
 * Created by ayushbagaria on 8/27/17.
 */

public class SplashPresenter implements SplashContract.Presenter {
    private SplashContract.View view;
    private SharedPreferencesUtil preference;

    @Override
    public void attachView(SplashContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {

    }

    @Override
    public void rxUnsubscribe() {

    }

    public void isLoggedIn() {
        if (view != null) {
            preference = SharedPreferencesUtil.getInstance(view.getContext());
            if (preference.getData(Constants.KEY.IS_LOGGED_IN,false)) {
                if (preference.getData(Constants.KEY.LOG_IN_MODE, -1) == Constants.VALUE.MODE_FB) {
                    view.startTrackingTheProfileAndAccessToken();
                }
                return;
            }

            view.moveToHomeActivty();
        }
    }

    @Override
    public void userIsLoggedIn() {

    }
}
