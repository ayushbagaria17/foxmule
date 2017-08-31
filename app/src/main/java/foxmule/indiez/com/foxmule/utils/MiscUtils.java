package foxmule.indiez.com.foxmule.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import foxmule.indiez.com.foxmule.Constants;
import foxmule.indiez.com.foxmule.domain.User;

/**
 * Created by ayushbagaria on 8/27/17.
 */
public class MiscUtils {


    public static boolean isLoggerOn() {
        return true;
    }

    public static void setUser(Context context, User user) {
        SharedPreferencesUtil prefs = SharedPreferencesUtil.getInstance(context);

        String userJson = GsonUtils.getJsonString(user);
        prefs.saveData(Constants.KEY.USER_JSON, userJson);
    }

    public static User getUser(Context context) {
        SharedPreferencesUtil prefs = SharedPreferencesUtil.getInstance(context);
        User user = GsonUtils.getObjectFromJson(prefs.getData(Constants.KEY.USER_JSON, null), User.class);
        return user;
    }

    public static boolean isConnectedToInternet(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}