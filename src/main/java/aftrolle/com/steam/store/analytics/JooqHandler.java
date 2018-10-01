package aftrolle.com.steam.store.analytics;

import aftrolle.com.steam.store.analytics.gen.Tables;
import aftrolle.com.steam.store.analytics.gen.tables.pojos.SteamTopSell;
import aftrolle.com.steam.store.analytics.gen.tables.records.SteamTopSellRecord;
import aftrolle.com.steam.store.analytics.loader.PropertiesFetcher;
import aftrolle.com.steam.store.analytics.scaper.toplist.TopListItem;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.util.postgres.PostgresDSL;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static aftrolle.com.steam.store.analytics.gen.Tables.STEAM_TOP_SELL;
import static org.jooq.impl.DSL.*;

public class JooqHandler {

    DSLContext context;


    public void connect() {

    }

   // final static Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());

    public void inserTopList(ArrayList<TopListItem> input) {

        final OffsetDateTime timeUTC = OffsetDateTime.now(Clock.systemUTC());



        PropertiesFetcher instance = PropertiesFetcher.getInstance();
        String jooqDBURL = "jdbc:postgresql://" + instance.db_url;
        try (Connection conn = DriverManager.getConnection(jooqDBURL, instance.db_account, instance.db_password)) {

            //context = PostgresDSL.using(conn);
            context = DSL.using(jooqDBURL, instance.db_account, instance.db_password);

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("FAIL");
        }

        ArrayList<SteamTopSellRecord> sellRecords = new ArrayList<>(input.size());

        for (TopListItem topListItem : input) {
            //   SteamTopSellRecord steamTopSell = context.newRecord(Tables.STEAM_TOP_SELL);
            SteamTopSellRecord steamTopSell = new SteamTopSellRecord();


            if (topListItem.appIds != null) {
                Integer[] appIDs = getArray(topListItem.appIds);
                steamTopSell.setAppIds(appIDs);
            }

            if (topListItem.bundleIds != null) {
                Integer[] bundleIDs = getArray(topListItem.bundleIds);
                steamTopSell.setBundleIds(bundleIDs);
            }
            if (topListItem.packageIds != null) {
                Integer[] packageIds = getArray(topListItem.packageIds);
                steamTopSell.setPackageIds(packageIds);
            }

            steamTopSell.setPosition(topListItem.position);
            steamTopSell.setPrevPrice(topListItem.prevPrice);
            steamTopSell.setTimestamp(timeUTC);
            steamTopSell.setPrice(topListItem.price);
            context.executeInsert(steamTopSell);
            System.out.println("done: "+ topListItem.position);
            // context.insertInto(steamTopSell).execute();
            InsertValuesStep2<SteamTopSellRecord, Integer, OffsetDateTime> step = context.insertInto(STEAM_TOP_SELL, STEAM_TOP_SELL.POSITION, STEAM_TOP_SELL.TIMESTAMP);
            InsertValuesStep2<SteamTopSellRecord, Integer, OffsetDateTime> values = step.values(23, timeUTC);
         //   values.execute();
/*
            context.insertInto(STEAM_TOP_SELL,
                    STEAM_TOP_SELL.TIMESTAMP,
                    STEAM_TOP_SELL.POSITION,
                    STEAM_TOP_SELL.PRICE,
                    STEAM_TOP_SELL.PREV_PRICE,
                    STEAM_TOP_SELL.APP_IDS,
                    STEAM_TOP_SELL.BUNDLE_IDS,
                    STEAM_TOP_SELL.PACKAGE_IDS).values(new Date(2)., topListItem.position, topListItem.price, topListItem.prevPrice, steamTopSell.getAppIds(), steamTopSell.getBundleIds(), steamTopSell.getPackageIds()).execute();
         */
            //   sellRecords.add(steamTopSell);
        }

        //  context.batchInsert(sellRecords).execute();
    }

    private Integer[] getArray(ArrayList<Integer> integers) {
        Integer[] stuff = new Integer[integers.size()];
        for (int i = 0; i < integers.size(); i++) {
            stuff[i] = integers.get(i);
        }
        return stuff;
    }
}
