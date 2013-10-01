package com.bettys.kitchen.recipes.app.interfaces;

import com.bettys.kitchen.recipes.app.models.Rss;

public interface BettysKitchenService {
    //@Get("/feed/")
    Rss getFeed();
}
