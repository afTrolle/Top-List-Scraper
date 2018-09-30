package steam.scraper.scrape;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import steam.scraper.Const;

import java.io.IOException;
import java.util.ArrayList;

public class TopList {

    public void parse(String url) throws IOException {
        Document doc = Jsoup.connect("https://store.steampowered.com/search/?filter=globaltopsellers&ignore_preferences=1").get();
        Elements newsHeadlines = doc.select(".search_result_row");
        for (Element headline : newsHeadlines) {
            String appIds = headline.attr("data-ds-appid");
            String bundleIds = headline.attr("data-ds-bundleid");
            String packgeIds = headline.attr("data-ds-packageid");
            System.out.println("appid: " +appIds +" bundleIDs: "+bundleIds +" packageids: "+packgeIds );
        }
    }



    public Document parseGlobalTopSellers(int page) throws IOException {
       String url ="https://store.steampowered.com/search/?ignore_preferences=1&os=win&filter=globaltopsellers&page="+page;
        Connection connection = Jsoup.connect(url).userAgent(Const.agent).timeout(5000).followRedirects(false);
        Document document = connection.get();
        return document;

    }

    public ArrayList<TopSellRow> appendRows(ArrayList<TopSellRow> sellRows, Document document){
        Elements itemRows = document.select(".search_result_row");
        for (Element row : itemRows) {
            sellRows.add(createTopSellRow(row));
        }
        return sellRows;
    }


    public TopSellRow createTopSellRow(Element row) {
        TopSellRow topSellRow = new TopSellRow();
        //set price
        Elements search_price = row.select(".search_price");
        String[] s = search_price.text().split(" ");
        if (s.length > 1){
            topSellRow.price = s[1];
            topSellRow.prevPrice = s[0];
        } else {
            topSellRow.price = s[0];
        }

        topSellRow.appIds = getIntArray(row,"data-ds-appid",",");
        topSellRow.bundleIds = getIntArray(row,"data-ds-bundleid",",");
        topSellRow.packageIds = getIntArray(row,"data-ds-packageid",",");
        return topSellRow;
    }

    private ArrayList<Integer> getIntArray(Element row , String attributeKey, String regex){
        String attrStr = row.attr(attributeKey);
        if (!attrStr.isEmpty()){
            ArrayList<Integer> attributes = new ArrayList<>();
            for (String s1 :  attrStr.split(regex)) {
                attributes.add(Integer.getInteger(s1));
            }
            return attributes;
        } else {
            return null;
        }
    }



    public int getNumPages(Document document){
        Elements pagination = document.select(".search_pagination");
        Elements a = pagination.select("a");
        Element last = a.get(a.size()-2);
        String text = last.text();
        System.out.println(text);

        return Integer.parseInt(text);
    }

}
