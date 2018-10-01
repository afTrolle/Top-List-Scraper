package aftrolle.com.steam.store.analytics.scaper.toplist;

import aftrolle.com.steam.store.analytics.loader.PropertiesFetcher;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TBDTest {

    @Test
    void parse() throws IOException {
        TBD tbd = new TBD();

        Document document = tbd.parseGlobalTopSellers(1);
        int numberOfItems = tbd.getNumItems(document);
        System.out.println();
        System.out.println(numberOfItems);
    }
}