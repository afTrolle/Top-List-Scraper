package aftrolle.com.steam.store.analytics.steam;

import aftrolle.com.steam.store.analytics.loader.PropertiesFetcher;
import com.ibasco.agql.core.messenger.WebMessenger;
import com.ibasco.agql.protocols.valve.steam.webapi.SteamStoreApiRequest;
import com.ibasco.agql.protocols.valve.steam.webapi.SteamWebApiClient;
import com.ibasco.agql.protocols.valve.steam.webapi.SteamWebApiRequest;
import com.ibasco.agql.protocols.valve.steam.webapi.SteamWebApiResponse;
import com.ibasco.agql.protocols.valve.steam.webapi.interfaces.SteamEconomy;
import com.ibasco.agql.protocols.valve.steam.webapi.interfaces.SteamStorefront;

public class SteamApi {

    SteamWebApiClient apiClient;

   public SteamApi(){
       String steamToken = PropertiesFetcher.getInstance().Steam_web_api_token;
       apiClient = new SteamWebApiClient(steamToken);
       SteamEconomy steamEconomy = new SteamEconomy(apiClient);
        SteamStorefront s = new SteamStorefront(apiClient);
       steamEconomy.getAssetPrices(22);
   }


}
