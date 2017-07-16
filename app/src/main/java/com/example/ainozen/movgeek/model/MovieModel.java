package com.example.ainozen.movgeek.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ainozen on 7/16/17.
 */

public class MovieModel implements Serializable {
    @SerializedName("id")
    public Long id;

    @SerializedName("title")
    public String title;

    @SerializedName("original_title")
    public String originalTitle;

    @SerializedName("overview")
    public String overview;

    @SerializedName("poster_path")
    public String posterPath;

    @SerializedName("backdrop_path")
    public String backdropPath;

    @SerializedName("vote_average")
    public Double voteAverage;

    @SerializedName("release_date")
    public String releaseDate;
}
