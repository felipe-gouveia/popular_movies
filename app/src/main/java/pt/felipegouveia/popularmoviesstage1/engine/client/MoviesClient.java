package pt.felipegouveia.popularmoviesstage1.engine.client;

import pt.felipegouveia.popularmoviesstage1.model.MovieResponse;
import pt.felipegouveia.popularmoviesstage1.model.generic.GenericPagination;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MoviesClient {

    @GET("movie/top_rated")
    Call<GenericPagination<MovieResponse>> listMoviesByMostRated(
            @Query("api_key") String apiKey,
            @Query("page") int page
    );

    @GET("movie/popular")
    Call<GenericPagination<MovieResponse>> listMoviesByPopular(
            @Query("api_key") String apiKey,
            @Query("page") int page
    );

}
