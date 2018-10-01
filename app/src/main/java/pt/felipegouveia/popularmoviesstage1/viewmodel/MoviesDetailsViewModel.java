package pt.felipegouveia.popularmoviesstage1.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import pt.felipegouveia.popularmoviesstage1.model.MovieResponse;

public class MoviesDetailsViewModel extends AndroidViewModel {

    private MovieResponse movie;

    public MovieResponse getMovie(){
        return movie;
    }

    public MoviesDetailsViewModel(Application application, MovieResponse movie) {
        super(application);
        this.movie = movie;
    }
}
