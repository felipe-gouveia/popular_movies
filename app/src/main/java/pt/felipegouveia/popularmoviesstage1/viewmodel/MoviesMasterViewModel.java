package pt.felipegouveia.popularmoviesstage1.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.paging.DataSource;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import pt.felipegouveia.popularmoviesstage1.engine.datasource.factory.DataSourceFactory;
import pt.felipegouveia.popularmoviesstage1.engine.datasource.DataSourceUtil;
import pt.felipegouveia.popularmoviesstage1.engine.datasource.MoviesDataSource;
import pt.felipegouveia.popularmoviesstage1.model.MovieResponse;


public class MoviesMasterViewModel extends AndroidViewModel {

    private static final String TAG = MoviesMasterViewModel.class.getSimpleName();
    public LiveData<PagedList<MovieResponse>> moviesPagedList;

    public LiveData<Boolean> noInternet;
    public DataSource<Integer, MovieResponse> dataSource;
    private DataSourceFactory dataSourceFactory;
    private int oldPos = -1;
    private String sortBy = "";

    @SuppressWarnings("unchecked")
    public MoviesMasterViewModel(@NonNull Application application) {
        super(application);

        if(sortBy.isEmpty()){
            sortBy = "popularity";
        }

        dataSourceFactory = new DataSourceFactory(MoviesDataSource.class, getApplication().getApplicationContext(), sortBy);
        dataSource = dataSourceFactory.create();

        transformation();

        moviesPagedList = new LivePagedListBuilder<>(dataSourceFactory, DataSourceUtil.setPagedListConfig(6))
                .build();

    }

    public void onSelectItem(AdapterView<?> parent, View view, int pos, long id)
    {
        if(oldPos != pos){
            oldPos = pos;
            switch (pos){
                case 0:
                    dataSourceFactory.sortBy = "popularity";
                    invalidateDataSource();
                    break;
                case 1:
                    dataSourceFactory.sortBy = "voteAverage";
                    invalidateDataSource();
                    break;
                default:
                    break;
            }
        }
    }

    private void transformation(){
        noInternet = Transformations.switchMap(dataSourceFactory.getLiveDataSource(), dataSource ->
                ((MoviesDataSource) dataSource).getNoInternet()
        );

    }

    public void invalidateDataSource() {
        if(moviesPagedList.getValue() != null){
            moviesPagedList.getValue().getDataSource().invalidate();
        }
        Log.d(TAG, "Invalidated dataSource.");
    }


}
