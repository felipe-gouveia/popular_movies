package pt.felipegouveia.popularmoviesstage1.engine.datasource;

import android.arch.paging.PagedList;

import pt.felipegouveia.popularmoviesstage1.BaseConstants;

public class DataSourceUtil {

    private DataSourceUtil(){}

    public static PagedList.Config setPagedListConfig(int prefetchDistance){

        return new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(BaseConstants.PAGE_SIZE)
                .setPrefetchDistance(prefetchDistance)
                .build();
    }

}