package foxmule.indiez.com.foxmule.onBoarding.enterConfirmationCode;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by ayushbagaria on 8/31/17.
 */

public class EnterConfirmationCodePresenter implements EnterConfirmationCodeContract.Presenter {
    private EnterConfirmationCodeContract.View view;
    private CompositeSubscription subscription;
    private String code;
    private String number;

    @Override
    public void attachView(EnterConfirmationCodeContract.View view) {
        this.view = view;
        this.subscription = new CompositeSubscription();
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public void rxUnsubscribe() {
        if (subscription.hasSubscriptions()) {
            subscription.unsubscribe();
        }
    }

    @Override
    public void validateCode(String s) {
        if (view != null) {
            if (code.equalsIgnoreCase(s)) view.moveToEnterUsernameView(code, number);
            else view.showInvalideConfirmationCodeError();
        }
    }

    @Override
    public void setNumberAndCode(String code, String number) {
        this.code = code;
        this.number = number;
    }

    @Override
    public void userAlreadyHasAnAccount() {
        view.moveToLoginView();
    }
}
