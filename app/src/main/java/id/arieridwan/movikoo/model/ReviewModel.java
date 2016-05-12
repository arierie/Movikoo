package id.arieridwan.movikoo.model;

import java.util.ArrayList;
import java.util.List;
/*
 * Created by Arie Ridwansyah on 5/10/16 6:07 AM
 * Copyright (c) 2016. All rights reserved.
 * enjoy your coding and drink coffee ^_^
 * Last modified 5/10/16 6:06 AM
 */
public class ReviewModel {

    private int id;
    private int page;
    private List<Result> results = new ArrayList<Result>();
    private int totalPages;
    private int totalResults;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

}