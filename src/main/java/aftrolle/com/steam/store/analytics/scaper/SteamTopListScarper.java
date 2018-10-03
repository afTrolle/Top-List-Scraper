package aftrolle.com.steam.store.analytics.scaper;

import aftrolle.com.steam.store.analytics.loader.PropertiesFetcher;
import aftrolle.com.steam.store.analytics.scaper.toplist.TBD;
import aftrolle.com.steam.store.analytics.scaper.toplist.TopListItem;

import java.io.IOException;
import java.util.ArrayList;

public class SteamTopListScarper {

    private final PropertiesFetcher properties = PropertiesFetcher.getInstance();
    private final Thread thread;
    private boolean scrape = false;
    private final TBD tbd;
    private SteamTopListScaperCallback callback;

    public SteamTopListScarper() throws IOException {
        thread = new Thread(scraperLooper);
        tbd = new TBD();
    }

    public void startScarperLoop(SteamTopListScaperCallback callback) {
        this.callback = callback;
        scrape = true;
        thread.start();
    }

    public void stopScraperLoop() {
        scrape = false;
    }

    private Runnable scraperLooper = () -> {
        final long fetchInterval = properties.top_page_get_interval;
        long timeSinceSleep = System.currentTimeMillis();
        while (scrape) {
            doWork();
            long timeElapsed = System.currentTimeMillis() - timeSinceSleep;
            if (timeElapsed < fetchInterval) {
                try {
                    Thread.sleep(fetchInterval - timeElapsed);
                } catch (InterruptedException e) {

                }
                timeSinceSleep = System.currentTimeMillis();
            }
        }
    };

    private void doWork() {
        try {
            ArrayList<TopListItem> topList = tbd.getTopList();
            callback.SteamTopListScaperCallback(topList);
        } catch (Exception e) {
         //   e.printStackTrace();
            //TODO FIX ERROR Handling.
        }
    }


}
