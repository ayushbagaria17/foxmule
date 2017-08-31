package foxmule.indiez.com.foxmule.onBoarding.enterPhoneNumber;

import foxmule.indiez.com.foxmule.BasePresenter;
import foxmule.indiez.com.foxmule.BaseView;
import foxmule.indiez.com.foxmule.domain.OtpResponse;

/**
 * Created by ayushbagaria on 8/30/17.
 */

public interface EnterPhoneNumberContract {

    interface  View extends BaseView {

        void showInvalidePhoneNumberError();

        void goToLoginView();

        void moveToOtpScreen(OtpResponse otpResponse);
    }

    interface Presenter extends BasePresenter<EnterPhoneNumberContract.View>  {

        void validateNumberAndGenerateCode(String phoneNumber);

        void userAlreadyHasAnAccount();
    }
}
