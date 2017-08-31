package foxmule.indiez.com.foxmule.domain;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ayushbagaria on 8/31/17.
 */

public class OtpResponse {

    @SerializedName("otp")
    String otp;

    public OtpResponse(String otp) {
        this.otp = otp;
    }

    public String getOtp() {

        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
