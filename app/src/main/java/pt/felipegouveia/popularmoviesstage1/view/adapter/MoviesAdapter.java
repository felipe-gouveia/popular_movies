package pt.felipegouveia.popularmoviesstage1.view.adapter;

import android.arch.paging.PagedListAdapter;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import pt.felipegouveia.popularmoviesstage1.R;
import pt.felipegouveia.popularmoviesstage1.databinding.ItemMoviesBinding;
import pt.felipegouveia.popularmoviesstage1.model.MovieResponse;
import pt.felipegouveia.popularmoviesstage1.view.ui.callbacks.MoviesClickCallback;

public class MoviesAdapter extends PagedListAdapter<MovieResponse, MoviesAdapter.MoviesViewHolder> {

    @Nullable
    private final MoviesClickCallback moviesClickCallback;

    public MoviesAdapter(@NonNull MoviesClickCallback moviesClickCallback) {
        super(DIFF_CALLBACK);
        this.moviesClickCallback = moviesClickCallback;
    }

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemMoviesBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_movies,
                        viewGroup, false);
        binding.setCallback(moviesClickCallback);
        return new MoviesViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder holder, int position) {
        if(getItem(position) != null){
            holder.bind(getItem(position));
        }
    }

    static class MoviesViewHolder extends RecyclerView.ViewHolder {

        final ItemMoviesBinding binding;

        MoviesViewHolder(ItemMoviesBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(MovieResponse movie){
            binding.setMovie(movie);
            binding.executePendingBindings();
        }
    }

    private static final DiffUtil.ItemCallback<MovieResponse> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<MovieResponse>() {
                @Override
                public boolean areItemsTheSame(@NonNull MovieResponse oldItem, @NonNull MovieResponse newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                @Override
                public boolean areContentsTheSame(@NonNull MovieResponse oldItem, @NonNull MovieResponse newItem) {
                   return oldItem.equals(newItem);
                }
            };

}

