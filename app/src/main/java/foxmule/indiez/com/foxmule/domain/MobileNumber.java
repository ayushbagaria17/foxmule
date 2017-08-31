package foxmule.indiez.com.foxmule.domain;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ayushbagaria on 8/31/17.
 */

public class MobileNumber {

    @SerializedName("numnber")
    String number;

    public MobileNumber(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
