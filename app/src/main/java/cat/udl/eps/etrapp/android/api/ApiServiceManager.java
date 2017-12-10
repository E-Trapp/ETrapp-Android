package cat.udl.eps.etrapp.android.api;

import java.io.IOException;

import cat.udl.eps.etrapp.android.controllers.UserController;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public class ApiServiceManager {

   private static String TOKEN_DATA = null;

   private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/etrapp-server/v1/")
            .client(getHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

   private static ApiService service = retrofit.create(ApiService.class);

   private static OkHttpClient getHttpClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(interceptor);

        final String token;
        if((token = UserController.getInstance().getCurrentToken()) != null) {
            httpClient.addInterceptor(chain -> {
                Request newRequest = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer " + token)
                        .build();
                return chain.proceed(newRequest);
            });
        }

        return httpClient.build();
    }

    public static ApiService getService() {
        final String token;
        if((token = UserController.getInstance().getCurrentToken()) != null && !token.equals(TOKEN_DATA)) {
            // Recreate Client in order to reload token data.
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:8080/etrapp-server/v1/")
                    .client(getHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            service = retrofit.create(ApiService.class);
        }
        return service;
    }

}
