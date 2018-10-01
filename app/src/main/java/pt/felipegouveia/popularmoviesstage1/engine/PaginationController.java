package pt.felipegouveia.popularmoviesstage1.engine;

import android.arch.paging.PageKeyedDataSource;

public interface PaginationController<T> {

    void list(int page, int size, String sortBy, PageKeyedDataSource.LoadInitialCallback<Integer, T> callback);
    void listAfter(int page, int size, String sortBy, PageKeyedDataSource.LoadCallback<Integer, T> callback);
}
