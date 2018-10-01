package pt.felipegouveia.popularmoviesstage1.engine.datasource;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.PageKeyedDataSource;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import pt.felipegouveia.popularmoviesstage1.BaseConstants;
import pt.felipegouveia.popularmoviesstage1.engine.ServiceUtil;
import pt.felipegouveia.popularmoviesstage1.engine.implementation.MoviesImplementation;
import pt.felipegouveia.popularmoviesstage1.model.MovieResponse;

public class MoviesDataSource extends PageKeyedDataSource<Integer, MovieResponse> {

    private MutableLiveData<Boolean> noInternet;

    private static final String TAG = MoviesDataSource.class.getSimpleName();

    private MoviesImplementation moviesImpl;
    private String sortBy;
    private Context context;

    public MoviesDataSource(String sortBy, Context context) {
        moviesImpl = new MoviesImplementation();
        this.sortBy = sortBy;
        this.context = context;
        noInternet = new MutableLiveData<>();
    }

    public MutableLiveData<Boolean> getNoInternet() {
        return noInternet;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, MovieResponse> callback) {
        if (ServiceUtil.isUserConnected(context)) {
            moviesImpl.list(BaseConstants.FIRST_PAGE, BaseConstants.PAGE_SIZE, sortBy, callback);
            getNoInternet().postValue(false);
        } else {
            getNoInternet().postValue(true);
            Log.d(TAG, "Tried to fetch initial data, but no internet connection was found.");
        }
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, MovieResponse> callback) {
        // method should not be implemented
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, MovieResponse> callback) {
        if (ServiceUtil.isUserConnected(context)) {
            moviesImpl.listAfter(params.key, BaseConstants.PAGE_SIZE, sortBy, callback);
            getNoInternet().postValue(false);
        } else {
            getNoInternet().postValue(true);
            Log.d(TAG, "Tried to fetch after data, but no internet connection was found.");
        }
    }
}

