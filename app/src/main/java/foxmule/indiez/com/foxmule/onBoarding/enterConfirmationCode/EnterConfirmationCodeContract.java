package foxmule.indiez.com.foxmule.onBoarding.enterConfirmationCode;

import foxmule.indiez.com.foxmule.BasePresenter;
import foxmule.indiez.com.foxmule.BaseView;

/**
 * Created by ayushbagaria on 8/31/17.
 */

public interface EnterConfirmationCodeContract {

    interface View  extends BaseView{

        void moveToEnterUsernameView(String code, String number);

        void moveToLoginView();

        void showInvalideConfirmationCodeError();
    }

    interface Presenter extends BasePresenter<EnterConfirmationCodeContract.View>{

        void validateCode(String s);


        void setNumberAndCode(String code, String number);

        void userAlreadyHasAnAccount();
    }


}
