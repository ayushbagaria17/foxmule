package foxmule.indiez.com.foxmule.communication;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import foxmule.indiez.com.foxmule.communication.apiInterfaces.IUser;
import foxmule.indiez.com.foxmule.domain.OtpResponse;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import rx.Single;

/**
 * Created by ayushbagaria on 8/27/17.
 */

public class RestClient implements IUser {
    static RestClient instance;
    Retrofit userServiceRestAdapter;

    public static  RestClient newInstance() {
        if (instance != null) {
            return instance;
        }
        instance = new RestClient();
        return instance;
    }

    public RestClient() {
        OkHttpClient.Builder client = getHttpClient();

        userServiceRestAdapter = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .baseUrl(ApiConstants.API.BASE_URL)
                .client(client.build())
                .build();
    }

    @NonNull
    private OkHttpClient.Builder getHttpClient() {
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
        client.connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS);
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        client.addInterceptor(logging);
        return client;
    }

    @Override
    public Single<OtpResponse> getOtp(@Body Map<String, String> body) {
        IUser service = userServiceRestAdapter.create(IUser.class);
        return service.getOtp(body);
    }
}
