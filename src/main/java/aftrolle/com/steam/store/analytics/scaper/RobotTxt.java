package aftrolle.com.steam.store.analytics.scaper;

import aftrolle.com.steam.store.analytics.loader.PropertiesFetcher;
import com.panforge.robotstxt.RobotsTxt;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class RobotTxt {

    private RobotsTxt robotsTxt;
    private PropertiesFetcher properties = PropertiesFetcher.getInstance();


    private void test() {
        //   RobotsTxt robots = getRobots("https://store.steampowered.com/robots.txt");
    }

    public RobotTxt(String url) throws IOException {
        InputStream robotsTxtStream = new URL(url).openStream();
        robotsTxt = RobotsTxt.read(robotsTxtStream);
    }

    public boolean isQuery(String url) {
        return robotsTxt.query(properties.scaper_agent, "https://store.steampowered.com/search/?os=win&filter=globaltopsellers");
    }

}
