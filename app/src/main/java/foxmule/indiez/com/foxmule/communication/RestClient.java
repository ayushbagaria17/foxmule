package foxmule.indiez.com.foxmule.communication;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by ayushbagaria on 8/27/17.
 */

public class RestClient {
    static RestClient instance;

    public RestClient() {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Request r = originalRequest.newBuilder().build();
                Log.d("Sending Request ..." , originalRequest.toString());
                Response response = chain.proceed(originalRequest);

                Log.d("Response ..." , response.toString());
                return response;


            }
        });
        client.connectTimeout(60, java.util.concurrent.TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS);
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        client.addInterceptor(logging);
    }
}
