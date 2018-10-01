package pt.felipegouveia.popularmoviesstage1.view.ui;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;

import pt.felipegouveia.popularmoviesstage1.R;
import pt.felipegouveia.popularmoviesstage1.model.MovieResponse;
import pt.felipegouveia.popularmoviesstage1.viewmodel.MoviesDetailsViewModel;
import pt.felipegouveia.popularmoviesstage1.databinding.FragmentMovieDetailBinding;

public class MovieDetailsFragment extends Fragment {

    private static final String TAG = MovieDetailsFragment.class.getSimpleName();

    private FragmentMovieDetailBinding binding;
    private MoviesDetailsViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            viewModel = ViewModelProviders.of(this, new TeamsViewModelFactory(getActivity().getApplication(),
                    Objects.requireNonNull(bundle.getParcelable("Movie")))).get(MoviesDetailsViewModel.class);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_detail, container, false);
        binding.setViewModel(viewModel);
        toolbar();
        return binding.getRoot();
    }

    private void toolbar() {
        ((AppCompatActivity)getActivity()).setSupportActionBar(binding.toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(viewModel.getMovie().getTitle() + " (" + viewModel.getMovie().getReleaseDate().substring(0,4) +")");
    }

    class TeamsViewModelFactory implements ViewModelProvider.Factory {

        private Application application;
        private MovieResponse movie;

        TeamsViewModelFactory(Application application, MovieResponse movie) {
            this.application = application;
            this.movie = movie;
            Log.d(TAG, "My movie: " + movie.getTitle());
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new MoviesDetailsViewModel(application, movie);
        }
    }
}
