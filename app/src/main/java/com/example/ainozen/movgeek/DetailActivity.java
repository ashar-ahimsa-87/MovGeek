package com.example.ainozen.movgeek;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ainozen.movgeek.app.ConstantVariable;
import com.example.ainozen.movgeek.model.MovieModel;

import org.w3c.dom.Text;

public class DetailActivity extends AppCompatActivity {

    private TextView mTvTitle, mTvReleaseDate, mTvRating, mTvSynopsis;
    private ImageView mIvThumbImg;

    private MovieModel movieGlobal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvReleaseDate = (TextView) findViewById(R.id.tv_release_date);
        mTvRating = (TextView) findViewById(R.id.tv_rating);
        mTvSynopsis = (TextView) findViewById(R.id.tv_synopsis);
        mIvThumbImg = (ImageView) findViewById(R.id.iv_thumb_img);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra(ConstantVariable.INTENT_OBJ_MOVIE)) {
                movieGlobal = (MovieModel) intentThatStartedThisActivity.getSerializableExtra(ConstantVariable.INTENT_OBJ_MOVIE);
                mTvTitle.setText(movieGlobal.title);
                mTvReleaseDate.setText(movieGlobal.releaseDate);
                mTvRating.setText(String.valueOf(movieGlobal.voteAverage) + "/" + ConstantVariable.MAX_RATING);
                mTvSynopsis.setText(movieGlobal.overview);

                Glide.with(this)
                        .load(ConstantVariable.URL_PHOTO + movieGlobal.posterPath)
                        .crossFade()
                        .into(mIvThumbImg);
            }
        }
    }

}
