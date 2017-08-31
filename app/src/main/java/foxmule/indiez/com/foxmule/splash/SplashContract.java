package foxmule.indiez.com.foxmule.splash;

import foxmule.indiez.com.foxmule.BasePresenter;
import foxmule.indiez.com.foxmule.BaseView;

/**
 * Created by ayushbagaria on 8/27/17.
 */

public interface SplashContract {

    interface View extends BaseView {

        void startTrackingTheProfileAndAccessToken();

        void moveToHomeActivty();
    }

    interface Presenter extends BasePresenter<SplashContract.View> {

        void userIsLoggedIn();
    }
}
