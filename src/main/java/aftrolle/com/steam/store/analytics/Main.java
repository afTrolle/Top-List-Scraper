package aftrolle.com.steam.store.analytics;

import aftrolle.com.steam.store.analytics.loader.PropertiesFetcher;
import aftrolle.com.steam.store.analytics.scaper.SteamTopListScarper;


public class Main {

    static SteamTopListScarper scaper;
    static  JooqHandler jooqHandler;

    public static void main(String[] args) {
        PropertiesFetcher.getInstance();
        jooqHandler = new JooqHandler();
        jooqHandler.connect();
        scaper = new SteamTopListScarper();

        scaper.startScarperLoop((input) -> {
            //Called at set interval contains list of top selling items.

            jooqHandler.inserTopList(input);
        });

    }


}
