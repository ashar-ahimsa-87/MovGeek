package com.example.ainozen.movgeek.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ainozen.movgeek.R;
import com.example.ainozen.movgeek.app.ConstantVariable;
import com.example.ainozen.movgeek.model.MovieModel;

import java.util.List;

/**
 * Created by ainozen on 7/16/17.
 */

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder> {

    private List<MovieModel> mMovieData;
    private Context context;
    private final OnItemClickListener listener;

    private int mLastAnimatedItemPosition = -1;

    public interface OnItemClickListener {
        void onItemClick(MovieModel item);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView ivPosterImg;

        public ViewHolder(View view) {
            super(view);
            ivPosterImg = (ImageView) view.findViewById(R.id.iv_poster_img);
        }

        public void bind(final MovieModel item,
                         final View.OnClickListener clickListener,
                         Context c) {
            //if(StringUtil.isNotNull(item.productPhoto)){
                Glide.with(c)
                        .load(ConstantVariable.URL_PHOTO + item.posterPath)
                        .crossFade()
                        .into(ivPosterImg);
            //}

            itemView.setOnClickListener(clickListener);
        }
    }

    public MovieListAdapter(List<MovieModel> mMovieData, Context context, OnItemClickListener listener) {
        this.mMovieData = mMovieData;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public MovieListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_list, parent, false);
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(MovieListAdapter.ViewHolder holder, int position) {
        final MovieModel mm = mMovieData.get(position);
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(mm);
            }
        };
        holder.bind(mm, clickListener, context);

        if(mLastAnimatedItemPosition < position){
            animateItem(holder.itemView);
            mLastAnimatedItemPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return mMovieData.size();
    }

    public void swap(List<MovieModel> dataSetParam){
        this.mMovieData.clear();
        this.mMovieData.addAll(dataSetParam);

        notifyDataSetChanged();
    }

    private void animateItem(View view) {
        view.animate()
                .translationY(0)
                .setInterpolator(new DecelerateInterpolator(3.f))
                .setDuration(700)
                .start();
    }
}
