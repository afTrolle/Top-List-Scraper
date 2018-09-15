package steam.scraper;

import org.jsoup.nodes.Document;
import steam.scraper.scrape.TopList;
import steam.scraper.scrape.TopSellRow;

import java.io.IOException;
import java.util.ArrayList;

public class Main {



    public static void main(String[] args) throws IOException, InterruptedException {

        TopList topList = new TopList();
        Document document = topList.parseGlobalTopSellers(1);
        int numPages = topList.getNumPages(document);
        System.out.println("num Pages: " +numPages);
      //  long time = System.currentTimeMillis();

        ArrayList<TopSellRow> sellRows  = new ArrayList<>();
        topList.appendRows(sellRows,document);
        System.out.println("start" );
        long timeSinceSleep = System.currentTimeMillis();
        long timeElapsedBetween = 1000 / 10;
        for (int i = 2; i < numPages+1; i++) {
            Document document1 = topList.parseGlobalTopSellers(i);
            topList.appendRows(sellRows,document1);
            System.out.println("on Page: "+i );


            long elapsedTime =  System.currentTimeMillis() - timeSinceSleep;
            if (elapsedTime < timeElapsedBetween){
                long timeToSleep = timeElapsedBetween - elapsedTime;
                Thread.sleep(timeToSleep);
            }
            timeSinceSleep = System.currentTimeMillis();
        }
    }
}
