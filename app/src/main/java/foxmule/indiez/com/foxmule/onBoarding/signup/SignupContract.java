package foxmule.indiez.com.foxmule.onBoarding.signup;

import foxmule.indiez.com.foxmule.BasePresenter;
import foxmule.indiez.com.foxmule.BaseView;

/**
 * Created by ayushbagaria on 8/28/17.
 */

public interface SignupContract {

    interface View extends BaseView{

        void moveToHomeActivity();
    }

    interface Presenter extends BasePresenter<SignupContract.View>{

        void userLoggedInUsingFb();
    }
}
