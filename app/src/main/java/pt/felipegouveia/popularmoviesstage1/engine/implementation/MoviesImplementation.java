package pt.felipegouveia.popularmoviesstage1.engine.implementation;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.PageKeyedDataSource;
import android.util.Log;

import pt.felipegouveia.popularmoviesstage1.BaseConstants;
import pt.felipegouveia.popularmoviesstage1.BuildConfig;
import pt.felipegouveia.popularmoviesstage1.engine.MoviesController;
import pt.felipegouveia.popularmoviesstage1.engine.ServiceUtil;
import pt.felipegouveia.popularmoviesstage1.engine.client.MoviesClient;
import pt.felipegouveia.popularmoviesstage1.model.MovieResponse;
import pt.felipegouveia.popularmoviesstage1.model.generic.GenericPagination;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesImplementation implements MoviesController {


    private final static String TAG = MoviesImplementation.class.getSimpleName();

    private MoviesClient client = ServiceUtil.getRetrofitClient().create(MoviesClient.class);

    @Override
    public void list(int page, int size, String sortBy, PageKeyedDataSource.LoadInitialCallback callback) {

        Call<GenericPagination<MovieResponse>> call;

        if(sortBy.equals("popularity")){
            call = client.listMoviesByPopular(BuildConfig.MOVIE_DB_API_KEY, page);
        } else if (sortBy.equals("voteAverage")) {
            call = client.listMoviesByMostRated(BuildConfig.MOVIE_DB_API_KEY, page);
        } else { //handle default
            call = client.listMoviesByPopular(BuildConfig.MOVIE_DB_API_KEY, page);
        }
        call.enqueue(new Callback<GenericPagination<MovieResponse>>() {
            @Override
            public void onResponse(Call<GenericPagination<MovieResponse>> call, Response<GenericPagination<MovieResponse>> response) {
                if(response.body() != null){
                    callback.onResult(response.body().getResults(), null, page + 1);
                } else {
                    Log.d(TAG, "Results body came back null, make sure you have set a valid API key at the gradle.properties file.");
                }
            }

            @Override
            public void onFailure(Call<GenericPagination<MovieResponse>> call, Throwable t) {
                Log.d(TAG, "No internet");
            }
        });
    }

    @Override
    public void listAfter(int page, int size, String sortBy, PageKeyedDataSource.LoadCallback callback) {
        Call<GenericPagination<MovieResponse>> call;

        if(sortBy.equals("popularity")){
            call = client.listMoviesByPopular(BuildConfig.MOVIE_DB_API_KEY, page);
        } else if (sortBy.equals("voteAverage")) {
            call = client.listMoviesByMostRated(BuildConfig.MOVIE_DB_API_KEY, page);
        } else { //handle default
            call = client.listMoviesByPopular(BuildConfig.MOVIE_DB_API_KEY, page);
        }

        call.enqueue(new Callback<GenericPagination<MovieResponse>>() {
            @Override
            public void onResponse(Call<GenericPagination<MovieResponse>> call, Response<GenericPagination<MovieResponse>> response) {
                if(response.body() != null){
                    Integer nextPage = (page > 0) ? page + 1 : null;
                    callback.onResult(response.body().getResults(), nextPage);
                } else {
                    Log.d(TAG, "Results body came back null, make sure you have set a valid API key at the BaseConstants file.");
                }
            }

            @Override
            public void onFailure(Call<GenericPagination<MovieResponse>> call, Throwable t) {

            }
        });
    }

}
