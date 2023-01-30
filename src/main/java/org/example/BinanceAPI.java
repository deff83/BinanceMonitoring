package org.example;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.general.Asset;

import java.util.ArrayList;
import java.util.List;

public class BinanceAPI {
    private List<String> assetList = new ArrayList<>();
    private BinanceApiRestClient client;
    private String API_KEY = "UeiTyPITOEpG5LpsOQrGgTTyhyrrOSycSOxIKdbB9dcsRCKnYJb3Pe0dDXEEefWoz";
    private String SECRET = "ctkiXCzjpsbmAtP4EmCecKJ4dpcVMKxxq0B6NrNDICg4SawFApTkFJjtzyWQAN1lV";
    private static BinanceAPI binanceAPI = new BinanceAPI();
    private BinanceAPI(){
        BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance(API_KEY, SECRET);
        client = factory.newRestClient();

    }

    public static BinanceAPI getBinanceAPI(){
        return binanceAPI;
    }

    public BinanceApiRestClient getClient(){return client;}

    public List<String> getAssetList() {
        return assetList;
    }

    public void setAssetList(List<String> assetList) {
        this.assetList = assetList;
    }
}
