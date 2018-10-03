package aftrolle.com.steam.store.analytics;

import aftrolle.com.steam.store.analytics.gen.tables.records.SteamTopSellRecord;
import aftrolle.com.steam.store.analytics.loader.PropertiesFetcher;
import aftrolle.com.steam.store.analytics.scaper.toplist.TopListItem;
import org.jooq.*;
import org.jooq.impl.DSL;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.ArrayList;


public class JooqHandler {


    public void insertTopList(ArrayList<TopListItem> input) throws Exception {

        //get time around reading in UTC
        final OffsetDateTime timeUTC = OffsetDateTime.now(Clock.systemUTC());

        //make connection to DB
        DSLContext context = connectToDB();
       // createTable(context);

        ArrayList<SteamTopSellRecord> sellRecords = convertListItemsToSellRecords(input, timeUTC);
        context.batchInsert(sellRecords).execute();

        //close connection to DB
        context.close();
    }

    public DSLContext connectToDB() throws Exception {
        PropertiesFetcher instance = PropertiesFetcher.getInstance();
        String jooqDBURL = "jdbc:postgresql://" + instance.db_url;

        //connect to DB
        return DSL.using(jooqDBURL, instance.db_account, instance.db_password);
    }

    private ArrayList<SteamTopSellRecord> convertListItemsToSellRecords(ArrayList<TopListItem> input, final OffsetDateTime timeUTC) throws Exception {
        ArrayList<SteamTopSellRecord> sellRecords = new ArrayList<>(input.size());

        for (TopListItem topListItem : input) {
            //   SteamTopSellRecord steamTopSell = context.newRecord(Tables.STEAM_TOP_SELL);
            SteamTopSellRecord steamTopSellRecord = convertToSteamTopSellRecord(topListItem, timeUTC);
            sellRecords.add(steamTopSellRecord);
            // context.executeInsert(steamTopSellRecord);
        }
        return sellRecords;
    }

    private SteamTopSellRecord convertToSteamTopSellRecord(final TopListItem topListItem, final OffsetDateTime timeUTC) throws Exception {
        SteamTopSellRecord steamTopSell = new SteamTopSellRecord();

        Integer[] appIDs = getArray(topListItem.appIds);
        steamTopSell.setAppIds(appIDs);

        Integer[] bundleIDs = getArray(topListItem.bundleIds);
        steamTopSell.setBundleIds(bundleIDs);

        Integer[] packageIds = getArray(topListItem.packageIds);
        steamTopSell.setPackageIds(packageIds);

        steamTopSell.setPosition(topListItem.position);
        steamTopSell.setTimestamp(timeUTC);

        steamTopSell.setPrice(topListItem.price);
        steamTopSell.setPrevPrice(topListItem.prevPrice);

        return steamTopSell;
    }

    private Integer[] getArray(ArrayList<Integer> integers) throws Exception {
        if (integers == null) {
            return null;
        }
        Integer[] stuff = new Integer[integers.size()];
        for (int i = 0; i < integers.size(); i++) {
            stuff[i] = integers.get(i);
        }
        return stuff;
    }

    //jooq isn't really built for generating dbs
    public void createTable(DSLContext context) throws Exception {
     //   context.createSchemaIfNotExists(Tables.STEAM_TOP_SELL.)
     //   context.createTableIfNotExists(Tables.STEAM_TOP_SELL).columns(Tables.STEAM_TOP_SELL.fields()).constraint(Tables.STEAM_TOP_SELL.con).execute();
     //   context.createTableIfNotExists(Tables.STEAM_TOP_SELL).as(Tables.STEAM_TOP_SELL.getSchema());
    }
}
