package aftrolle.com.steam.store.analytics.loader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertiesFetcher {

    static private PropertiesFetcher propertiesFetcher = null;

    public static PropertiesFetcher getInstance() throws IOException {
        if (propertiesFetcher == null) {
            propertiesFetcher = new PropertiesFetcher();
        }
        return propertiesFetcher;
    }

    //Steam web API
    public final String Steam_web_api_token;

    //top seller page
    public final long top_page_get_interval;
    public final long top_page_get_amount;
    public final String scaper_agent;

    //postgres DB
    public final String db_url;
    public final String db_account;
    public final String db_password;


    PropertiesFetcher() throws IOException {
        Properties prop = new Properties();
        File file = null;
        file = new File("./app_settings.properties");

        if (file == null || !(file.exists() && file.isFile())) {
            file = new File("./src/main/resources/app_settings.properties");
        }

        prop.load(new FileInputStream(file));

        Steam_web_api_token = prop.getProperty("steam_web_api_token");

        top_page_get_interval = Long.valueOf(prop.getProperty("top_page_get_interval"));
        top_page_get_amount = Long.valueOf(prop.getProperty("top_page_get_amount"));
        scaper_agent = prop.getProperty("scarper_agent");

        db_url = prop.getProperty("db_url");
        db_account = prop.getProperty("db_account");
        db_password = prop.getProperty("db_password");
    }
}
