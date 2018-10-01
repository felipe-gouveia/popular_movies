package pt.felipegouveia.popularmoviesstage1.model.generic;

import java.util.List;

public class GenericPagination<T> {

    private List<T> results;

    public List<T> getResults() {
        return results;
    }

    public void setContent(List<T> results) {
        this.results = results;
    }
}
