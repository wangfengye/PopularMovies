package com.example.feng.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by feng on 2017/9/17.
 */

public class ThumbnailAdapter extends RecyclerView.Adapter<ThumbnailAdapter.ViewHolder> implements Comparator<Movie> {
    private static final int POPULAR = 0;
    private static final int VOTE = 1;
    private List<Movie> mMoviesAll;//all movies
    private List<Movie> mMovies;// show movies;
    private Context mContext;
    private Boolean mIsConnection = false;

    private int mSort = POPULAR;

    public ThumbnailAdapter(Context context) {
        this.mContext = context;
        mMoviesAll = new ArrayList<>();
        mMovies = new ArrayList<>();
    }

    public void reSetData(List<Movie> movies) {
        mMoviesAll.clear();
        mMoviesAll.addAll(movies);
        reSetData();
    }

    private void reSetData() {

        mMovies.clear();
        //筛选
        if (mIsConnection) {
            for (Movie movie : mMoviesAll) {
                if (movie.getCollection()) {
                    mMovies.add(movie);
                }
            }
        } else {
            mMovies.addAll(mMoviesAll);
        }
        Log.i("TAG", "reSetDawwwta: "+mMovies.size());

        //排序
        Collections.sort(mMovies, this);
        notifyDataSetChanged();
    }

    public void showCollection(boolean is) {
        mIsConnection = is;
        reSetData();
    }

    public void setSort(int type) {
        mSort = type;
        reSetData();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_thumbnail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Movie movie = mMovies.get(position);
        Glide.with(mContext)
                .load(mMovies.get(position).getPoster())
                .into(holder.img);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(DetailActivity.MOVIE, movie);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    // sort function
    @Override
    public int compare(Movie movie, Movie t1) {
        switch (mSort) {
            case POPULAR:
                if (movie.getPopularity() - t1.getPopularity() > 0) {
                    return -1;
                } else if (movie.getPopularity() - t1.getPopularity() == 0) {
                    return 0;
                } else {
                    return 1;
                }
            case VOTE:
                if (movie.getVoteAverage() - t1.getVoteAverage() > 0) {
                    return -1;
                } else if (movie.getVoteAverage() - t1.getVoteAverage() == 0) {
                    return 0;
                } else {
                    return 1;
                }
            default:
                break;
        }
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
        }
    }
}
