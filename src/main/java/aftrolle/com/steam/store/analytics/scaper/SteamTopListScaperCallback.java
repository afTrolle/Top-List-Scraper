package aftrolle.com.steam.store.analytics.scaper;

import aftrolle.com.steam.store.analytics.scaper.toplist.TopList;
import aftrolle.com.steam.store.analytics.scaper.toplist.TopListItem;

import java.util.ArrayList;

public interface SteamTopListScaperCallback {
    public void SteamTopListScaperCallback(ArrayList<TopListItem> topList);
}
