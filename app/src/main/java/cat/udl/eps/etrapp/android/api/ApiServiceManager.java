package cat.udl.eps.etrapp.android.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiServiceManager {

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://rysite.eu/etrapp/")
            .client(getHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private static ApiService service = retrofit.create(ApiService.class);

    private static OkHttpClient getHttpClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(interceptor)
                .build();
        return httpClient.build();
    }

    public static ApiService getService() {
        return service;
    }

}
