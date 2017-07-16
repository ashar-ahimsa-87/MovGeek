package com.example.ainozen.movgeek.service;

import com.example.ainozen.movgeek.model.MovieModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ainozenbook on 9/15/2016.
 */
public class DefaultResponse {
    @SerializedName("page")
    private Long page;

    @SerializedName("total_results")
    private Long totalResults;

    @SerializedName("total_pages")
    private Long totalPages;

    @SerializedName("results")
    private List<MovieModel> movies;

    public Long getPage() {
        return page;
    }

    public void setPage(Long page) {
        this.page = page;
    }

    public Long getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Long totalResults) {
        this.totalResults = totalResults;
    }

    public Long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Long totalPages) {
        this.totalPages = totalPages;
    }

    public List<MovieModel> getMovies() {
        return movies;
    }

    public void setMovies(List<MovieModel> movies) {
        this.movies = movies;
    }
}
