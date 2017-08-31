package foxmule.indiez.com.foxmule.onBoarding.enterPhoneNumber;

import android.telephony.PhoneNumberUtils;

import java.util.HashMap;
import java.util.Map;

import foxmule.indiez.com.foxmule.Constants;
import foxmule.indiez.com.foxmule.communication.RestClient;
import foxmule.indiez.com.foxmule.domain.OtpResponse;
import foxmule.indiez.com.foxmule.utils.MiscUtils;
import rx.Observer;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.exceptions.CompositeException;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by ayushbagaria on 8/30/17.
 */

public class EnterPhoneNumberPresenter implements EnterPhoneNumberContract.Presenter {
    private EnterPhoneNumberContract.View view;
    private CompositeSubscription subscription;

    @Override
    public void attachView(EnterPhoneNumberContract.View view) {
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
    public void validateNumberAndGenerateCode(String phoneNumber) {
        String mobilePatter = "[0-9]{10}$";
        if (phoneNumber.matches(mobilePatter) && MiscUtils.isConnectedToInternet(view.getContext())) {
            Map<String,String> body = new HashMap<>();
            body.put(Constants.KEY.NUMBER,phoneNumber);
            subscription.add(RestClient.newInstance().getOtp(body).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
                            //TODO show loading view
                        }
                    }).subscribe(new Observer<OtpResponse>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            //TODO
                        }

                        @Override
                        public void onNext(OtpResponse otpResponse) {

                            //TODO hide loading view
                            view.moveToOtpScreen(otpResponse);
                        }
                    }));

        } else  if (!phoneNumber.matches(mobilePatter)){
            view.showInvalidePhoneNumberError();
        } else {
           //TODO Show no iternet dialog
        }
    }

    @Override
    public void userAlreadyHasAnAccount() {
        if (view != null ) view.goToLoginView();
    }
}
