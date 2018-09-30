package steam.scraper.steam;

import com.ibasco.agql.protocols.valve.steam.webapi.SteamWebApiClient;
import com.ibasco.agql.protocols.valve.steam.webapi.interfaces.SteamEconomy;
import com.ibasco.agql.protocols.valve.steam.webapi.pojos.SteamAssetPriceInfo;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

public class SteamLib {


    public  SteamLib(){
        String token = "D4E700CAAB3D657F28C5123D1472554C";
        SteamWebApiClient steamClient = new SteamWebApiClient(token);
        SteamEconomy steamEconomy = new SteamEconomy(steamClient);


        try {
            List<SteamAssetPriceInfo> steamAssetPriceInfos = steamEconomy.getAssetPrices(222).get();
            for (SteamAssetPriceInfo steamAssetPriceInfo : steamAssetPriceInfos) {
                Map<String, Integer> prices = steamAssetPriceInfo.getPrices();
                for (Map.Entry<String, Integer> stringIntegerEntry : prices.entrySet()) {
                    System.out.println(stringIntegerEntry.getKey() + " "+stringIntegerEntry.getValue());
                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
}
