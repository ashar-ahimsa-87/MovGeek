package com.example.ainozen.movgeek;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ainozen.movgeek.adapter.MovieListAdapter;
import com.example.ainozen.movgeek.app.ConstantVariable;
import com.example.ainozen.movgeek.model.MovieModel;
import com.example.ainozen.movgeek.service.DefaultResponse;
import com.example.ainozen.movgeek.service.RestClient;
import com.example.ainozen.movgeek.service.RestInterface;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRvMovieList;
    private TextView mTvErrorMessageDisplay;
    private ProgressBar mPbLoadingIndicator;
    private MovieListAdapter mMovieListAdapter;

    private static final String POPULAR_CATEGORY = "popular";
    private static final String TOP_RATED_CATEGORY = "top_rated";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRvMovieList = (RecyclerView) findViewById(R.id.rv_movie_list);
        mTvErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);
        mPbLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        mRvMovieList.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
        mRvMovieList.setHasFixedSize(true);

        mMovieListAdapter = new MovieListAdapter(new ArrayList<MovieModel>(), this, new MovieListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(MovieModel item) {
                Intent intentToStartDetailActivity = new Intent(MainActivity.this, DetailActivity.class);
                intentToStartDetailActivity.putExtra(ConstantVariable.INTENT_OBJ_MOVIE, item);
                startActivity(intentToStartDetailActivity);
            }
        });
        mRvMovieList.setAdapter(mMovieListAdapter);

        if(ConstantVariable.isNetworkAvailable(this))
            loadMovieData();
        else {
            showErrorMessage();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(ConstantVariable.isNetworkAvailable(this)){
            if (id == R.id.action_popular) {
                new FetchMovieTask().execute(POPULAR_CATEGORY);
                return true;
            } else if (id == R.id.action_top_rated) {
                new FetchMovieTask().execute(TOP_RATED_CATEGORY);
                return true;
            }
        } else {
            showErrorMessage();
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadMovieData() {
        showMovieDataView();
        new FetchMovieTask().execute(POPULAR_CATEGORY);
    }

    private void showMovieDataView() {
        mTvErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRvMovieList.setVisibility(View.VISIBLE);
    }

    /**
     * This method will make the error message visible and hide the weather
     * View.
     * <p>
     * Since it is okay to redundantly set the visibility of a View, we don't
     * need to check whether each view is currently visible or invisible.
     */
    private void showErrorMessage() {
        mRvMovieList.setVisibility(View.INVISIBLE);
        mTvErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    public class FetchMovieTask extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mPbLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(String... params) {
            if (params.length == 0) {
                return null;
            }

            RestInterface apiService =
                    RestClient.getClient().create(RestInterface.class);
            Call<DefaultResponse> call = null;
            if(params[0].equals(POPULAR_CATEGORY)) {
                call = apiService.getPopularMovie(getString(R.string.api_key));
            } else if(params[0].equals(TOP_RATED_CATEGORY)) {
                call = apiService.getTopRatedMovie(getString(R.string.api_key));
            }

            call.enqueue(new Callback<DefaultResponse>() {

                @Override
                public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                    List<MovieModel> movies = response.body().getMovies();
                    mPbLoadingIndicator.setVisibility(View.INVISIBLE);
                    mMovieListAdapter.swap(movies);
                    showMovieDataView();
                }

                @Override
                public void onFailure(Call<DefaultResponse> call, Throwable t) {
                    mPbLoadingIndicator.setVisibility(View.INVISIBLE);
                    mMovieListAdapter.swap(new ArrayList<MovieModel>());
                    showErrorMessage();
                }
            });

            return null;
        }
    }
}
