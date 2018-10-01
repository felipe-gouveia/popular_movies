package pt.felipegouveia.popularmoviesstage1.engine.datasource.factory;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;
import android.content.Context;

import pt.felipegouveia.popularmoviesstage1.engine.datasource.MoviesDataSource;


public class DataSourceFactory<T> extends DataSource.Factory<Integer, T> {

    private Class<T> sourceClass;
    private Context context;
    public String sortBy;

    private MutableLiveData<T> liveDataSource;

    public DataSourceFactory(Class<T> sourceClass, Context context, String sortBy) {
        this.sourceClass = sourceClass;
        this.context = context;
        this.sortBy = sortBy;
        liveDataSource = new MutableLiveData<>();
    }

    @SuppressWarnings("unchecked")
    @Override
    public DataSource<Integer, T> create() {
        T source = null;
        if(sourceClass == MoviesDataSource.class){
            source = (T) new MoviesDataSource(sortBy, context);
        }
        liveDataSource.postValue(source);
        return (DataSource<Integer, T>) source;
    }

    public MutableLiveData<T> getLiveDataSource() {
        return liveDataSource;
    }
}