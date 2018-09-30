package aftrolle.com.steam.store.analytics;

import aftrolle.com.steam.store.analytics.loader.PropertiesFetcher;
import aftrolle.com.steam.store.analytics.scaper.SteamTopListScarper;
import aftrolle.com.steam.store.analytics.scaper.SteamTopListScaperCallback;
import aftrolle.com.steam.store.analytics.scaper.TopList;

import java.util.concurrent.TimeUnit;


public class Main {

    static SteamTopListScarper scaper;

    public static void main(String[] args) {
        PropertiesFetcher.getInstance();
        scaper = new SteamTopListScarper();
        listScraperLoop();
    }

    private static void listScraperLoop() {

        final long fetchInterval = PropertiesFetcher.getInstance().top_page_get_interval;
        Runnable runnable = () -> {
            long timeSinceSleep = System.currentTimeMillis();
            while (true) {
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

        Thread thread = new Thread(runnable);
        thread.start();
    }

    private static void doWork() {
        System.out.println("doing Work");
    }


    public void SteamTopListScaperCallback(TopList topList) {

    }
}
