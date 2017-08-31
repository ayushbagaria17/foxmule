package foxmule.indiez.com.foxmule.communication.apiInterfaces;

import java.util.HashMap;
import java.util.Map;

import foxmule.indiez.com.foxmule.domain.OtpResponse;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Single;

/**
 * Created by ayushbagaria on 8/31/17.
 */

public interface IUser {


    @POST("login/phone/otp")
    Single<OtpResponse> getOtp(@Body Map<String,String> body);
}
