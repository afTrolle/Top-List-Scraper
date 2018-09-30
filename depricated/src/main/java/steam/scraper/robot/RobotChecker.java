package steam.scraper.robot;

import com.panforge.robotstxt.RobotsTxt;
import steam.scraper.Const;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class RobotChecker {
   private static final RobotChecker robo = new RobotChecker();

    public RobotChecker getRobotChecker(){
        return robo;
    }

    private void temp(){

        System.out.println("hello world");
        try (InputStream robotsTxtStream = new URL("https://store.steampowered.com/robots.txt").openStream()) {
            RobotsTxt robotsTxt = RobotsTxt.read(robotsTxtStream);
            boolean helloWorld = robotsTxt.query(Const.agent, "https://store.steampowered.com/search/?os=win&filter=globaltopsellers");
            if (helloWorld) {
                System.out.println("is okay");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
