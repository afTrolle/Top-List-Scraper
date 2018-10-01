package aftrolle.com.steam.store.analytics.scaper.toplist;

import aftrolle.com.steam.store.analytics.loader.PropertiesFetcher;
import aftrolle.com.steam.store.analytics.scaper.RobotTxt;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;

public class TBD {

    private static final PropertiesFetcher propertiesFetcher = PropertiesFetcher.getInstance();

    /*
    //Not used
    public void parse() throws IOException {
        Document doc = Jsoup.connect("https://store.steampowered.com/search/?filter=globaltopsellers&ignore_preferences=1").get();
        Elements newsHeadlines = doc.select(".search_result_row");
        for (Element headline : newsHeadlines) {
            String appIds = headline.attr("data-ds-appid");
            String bundleIds = headline.attr("data-ds-bundleid");
            String packgeIds = headline.attr("data-ds-packageid");
            System.out.println("appid: " + appIds + " bundleIDs: " + bundleIds + " packageids: " + packgeIds);
        }
    }
    */

    public Document parseGlobalTopSellers(int page) throws IOException {
        String url = "https://store.steampowered.com/search/?ignore_preferences=1&filter=globaltopsellers&page=" + page;
        Connection connection = Jsoup.connect(url).userAgent(propertiesFetcher.scaper_agent).timeout(5000).followRedirects(false);
        Document document = connection.get();
        return document;
    }

    public ArrayList<TopListItem> appendRows(ArrayList<TopListItem> sellRows, Document document) throws ParseException {
        Elements itemRows = document.select(".search_result_row");
        for (Element row : itemRows) {
            TopListItem topSellRow = createTopSellRow(row, sellRows.size() + 1);
            sellRows.add(topSellRow);
        }
        return sellRows;
    }


    public TopListItem createTopSellRow(Element row, int index) throws ParseException {
        TopListItem topSellRow = new TopListItem();
        //set price
        Elements search_price = row.select(".search_price");
        NumberFormat format = new DecimalFormat();

        String[] s = search_price.text().split(" ");
        if (s.length > 1) {
            topSellRow.price = format.parse((s[1].substring(0, s[1].length() - 1))).floatValue();
            topSellRow.prevPrice = format.parse(s[0].substring(0, s[0].length() - 1)).floatValue();
        } else {
            topSellRow.price = format.parse(s[0].substring(0, s[0].length() - 1)).floatValue();
        }

        topSellRow.appIds = getIntArray(row, "data-ds-appid", ",");
        topSellRow.bundleIds = getIntArray(row, "data-ds-bundleid", ",");
        topSellRow.packageIds = getIntArray(row, "data-ds-packageid", ",");
        topSellRow.position = index;
        return topSellRow;
    }


    private ArrayList<Integer> getIntArray(Element row, String attributeKey, String regex) {
        String attrStr = row.attr(attributeKey);
        if (!attrStr.isEmpty()) {
            ArrayList<Integer> attributes = new ArrayList<>();
            for (String s1 : attrStr.split(regex)) {
                attributes.add(Integer.parseInt(s1));
            }
            return attributes;
        } else {
            return null;
        }
    }

    public int getNumPages(Document document) {
        Elements pagination = document.select(".search_pagination");
        Elements a = pagination.select("a");
        Element last = a.get(a.size() - 2);
        String text = last.text();
        return Integer.parseInt(text);
    }

    protected int getNumItems(Document document) {
        Elements pagination = document.select(".search_pagination_left");
        String[] s = pagination.text().split(" ");
        String s1 = s[s.length - 1];
        return Integer.parseInt(s1);
    }


    public ArrayList<TopListItem> getTopList() throws IOException, ParseException {
        RobotTxt robotTxt = new RobotTxt("https://store.steampowered.com/robots.txt");
        boolean isOkayToGo = robotTxt.isQuery("https://store.steampowered.com/search/?ignore_preferences=1&filter=globaltopsellers&page=1");
        if (!isOkayToGo) {
            //Robots file doesn't want us to run.
            throw new IOException("RobotsTxt, Excluded us from https://store.steampowered.com/robots.txt");
        }

        ArrayList<TopListItem> topListItems = new ArrayList<>();

        Document document = parseGlobalTopSellers(1);
        int numberOfItems = getNumItems(document);
        long amountToFetch = propertiesFetcher.top_page_get_amount;
        if (numberOfItems < amountToFetch) {
            amountToFetch = numberOfItems;
        }

        appendRows(topListItems, document);

        for (int i = 2; topListItems.size() < amountToFetch; i++) {
            document = parseGlobalTopSellers(i);
            appendRows(topListItems, document);
        }

        return topListItems;
    }
}
