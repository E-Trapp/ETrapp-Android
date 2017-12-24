package cat.udl.eps.etrapp.android.api;

import cat.udl.eps.etrapp.android.controllers.UserController;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiServiceManager {

    private static String TOKEN_DATA = null;

    private static Retrofit retrofit;
    private static ApiService service;

    static {
        reset();
    }


    private static OkHttpClient getHttpClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(interceptor);

        final String token;
        if ((token = UserController.getInstance().getCurrentToken()) != null) {
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
        if ((token = UserController.getInstance().getCurrentToken()) != null && !token.equals(TOKEN_DATA)) {
            // Recreate Client in order to reload token data.
            TOKEN_DATA = token;
            reset();
        } else if (token == null && TOKEN_DATA != null) {
            TOKEN_DATA = null;
            reset();
        }
        return service;
    }

    private static void reset() {
        retrofit = new Retrofit.Builder()
                //.baseUrl("http://10.0.2.2:8080/etrapp-server/v1/")
                .baseUrl("http://172.16.100.20:8080/etrapp-server/v1/")
                .client(getHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(ApiService.class);
    }

}
