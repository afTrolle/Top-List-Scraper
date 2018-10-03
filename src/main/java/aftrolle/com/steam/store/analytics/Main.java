package aftrolle.com.steam.store.analytics;

import aftrolle.com.steam.store.analytics.loader.PropertiesFetcher;
import aftrolle.com.steam.store.analytics.scaper.SteamTopListScarper;

import java.io.IOException;


public class Main {

    static SteamTopListScarper scaper;
    static  JooqHandler jooqHandler;

    public static void main(String[] args) throws IOException {
        PropertiesFetcher.getInstance();
        jooqHandler = new JooqHandler();
        scaper = new SteamTopListScarper();

        scaper.startScarperLoop((input) -> {
            //Called at set interval contains list of top selling items.
            try {
                jooqHandler.insertTopList(input);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }


}
