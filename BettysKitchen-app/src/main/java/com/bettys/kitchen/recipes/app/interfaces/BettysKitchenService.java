package com.bettys.kitchen.recipes.app.interfaces;

import com.bettys.kitchen.recipes.app.models.Rss;

import retrofit.http.GET;

public interface BettysKitchenService {
    @GET("/feed/")
    Rss getFeed();
}
