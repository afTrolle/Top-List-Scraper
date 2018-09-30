package steam.scraper.scrape;


import java.util.ArrayList;

public class TopSellRow {

    public TopSellRow(){

    }

    //position on global top list
    int position = 0;
    // time fetched from steam website
    long fetchTime = 0;
    //app ids associated with row
    ArrayList<Integer> appIds;
    //bundle ids associated with row
    ArrayList<Integer> bundleIds;

    ArrayList<Integer> packageIds;

    String prevPrice;
    String price;
}
