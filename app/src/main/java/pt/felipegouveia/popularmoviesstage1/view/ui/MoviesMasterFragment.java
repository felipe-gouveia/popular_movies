package pt.felipegouveia.popularmoviesstage1.view.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;

import java.util.Objects;

import pt.felipegouveia.popularmoviesstage1.BaseConstants;
import pt.felipegouveia.popularmoviesstage1.R;
import pt.felipegouveia.popularmoviesstage1.databinding.FragmentMoviesMasterBinding;
import pt.felipegouveia.popularmoviesstage1.model.MovieResponse;
import pt.felipegouveia.popularmoviesstage1.util.Converter;
import pt.felipegouveia.popularmoviesstage1.view.adapter.MoviesAdapter;
import pt.felipegouveia.popularmoviesstage1.view.ui.callbacks.MoviesClickCallback;
import pt.felipegouveia.popularmoviesstage1.viewmodel.MoviesMasterViewModel;

public class MoviesMasterFragment extends Fragment {

    OnMovieSelectedListener activityCallback;

    private FragmentMoviesMasterBinding binding;
    private MoviesMasterViewModel viewModel;
    private MoviesAdapter adapter;
    private ArrayAdapter<CharSequence> spinnerAdapter;

    private final MoviesClickCallback moviesClickCallback = movie -> activityCallback.onMovieSelected(movie);

    //passes info to ListTeamsFragment using ManagementActivity
    public interface OnMovieSelectedListener {
        void onMovieSelected(MovieResponse movie);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(MoviesMasterViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movies_master, container, false);
        binding.recyclerMovies.setLayoutManager(new GridLayoutManager(getActivity(), 3, LinearLayoutManager.VERTICAL, false));
        ViewTreeObserver viewTreeObserver = binding.recyclerMovies.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(this::calculateSpanCount);
        adapter = new MoviesAdapter(moviesClickCallback);
        binding.recyclerMovies.setAdapter(adapter);
        viewModel.moviesPagedList.observe(this, movieResponses -> adapter.submitList(movieResponses));
        viewModel.noInternet.observe(this, bool -> binding.noInternet.setVisibility(bool ? View.VISIBLE : View.GONE));
        binding.setViewModel(viewModel);
        spinner();
        return binding.getRoot();
    }

    private void spinner() {
            spinnerAdapter = ArrayAdapter.createFromResource(getActivity(),
                    R.array.filters_array, android.R.layout.simple_spinner_item);
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            binding.spinnerFilter.setAdapter(spinnerAdapter);
    }

    private void calculateSpanCount() {
        int spanCount = (int) Math.floor(binding.recyclerMovies.getWidth() / Converter.convertDPToPixels(BaseConstants.MOVIE_ITEM_WIDTH, Objects.requireNonNull(getActivity())));
        ((GridLayoutManager) Objects.requireNonNull(binding.recyclerMovies.getLayoutManager())).setSpanCount(spanCount);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            activityCallback = (OnMovieSelectedListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + getString(R.string.must_implement_movies_selected_listener));
        }
    }
}
