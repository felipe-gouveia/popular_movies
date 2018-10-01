package pt.felipegouveia.popularmoviesstage1.engine;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceUtil {

    private final static String BASE_REQUEST_URL = "https://api.themoviedb.org/3/";

    public static String getBaseRequestUrl() {
        return BASE_REQUEST_URL;
    }

    public static Retrofit getRetrofitClient(){

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();

        return new Retrofit.Builder()
                .baseUrl(getBaseRequestUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    public static boolean isUserConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
