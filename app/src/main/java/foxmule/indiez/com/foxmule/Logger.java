package foxmule.indiez.com.foxmule;

import android.util.Log;

/**
 * Created by ayushbagaria on 8/27/17.
 */

public class Logger  {
    public static void logInfo (String info) {
        Log.d("Information", info);
    }

    public static void logError(String error) {
        Log.e("Information", error);
    }
}
