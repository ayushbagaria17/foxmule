package foxmule.indiez.com.foxmule.domain;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ayushbagaria on 8/31/17.
 */

public class RestResponse<T> {
    @SerializedName("responseData")
    private T data;

    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private boolean status;

    public String getMessage() {
        return message;
    }

    public boolean isStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
