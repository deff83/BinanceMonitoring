package org.example;

import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.market.Candlestick;
import com.binance.api.client.domain.market.CandlestickInterval;
import com.binance.api.client.domain.market.OrderBook;
import com.binance.api.client.domain.market.TickerStatistics;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Info extends Task {

    private Label label;

    public Info(Label label){
        this.label = label;
    }

    @Override
    protected Void call() throws Exception {

        String file ="C:\\Users\\artem\\Desktop\\jjj\\writeBinance.xls";

        Excel excel = null;
        excel = new Excel();

        try {
            excel.readFile(file);
        } catch (IOException e) {
            // throw new RuntimeException(e);
        }

        System.out.println("Hello world!");
        BinanceAPI binanceAPI = BinanceAPI.getBinanceAPI();
        BinanceApiRestClient client = binanceAPI.getClient();

        List<TickerStatistics> list2 = new ArrayList<>();

        while (list2.size()==0){
            try {
                list2 = client.getAll24HrPriceStatistics();
            }catch (Exception e) {
                new Problem(label, "Internet problem:" + "0: not list statistic24").problemInternet();
            }
        }

        List<String> assetList = new ArrayList<>();

        for (int k1=0; k1<list2.size(); k1++){
            TickerStatistics tickerPrice = list2.get(k1);
            String name = tickerPrice.getSymbol();
            updateProgress(k1, list2.size());
            final int o = k1;
            final int max_o = list2.size();
            Platform.runLater(new Runnable() {
                                  @Override
                                  public void run() {
                                      label.setText("Checking: "+name+"\t\t\t"+o + "/" + max_o);
                                  }
                              });
            //System.out.println(k1+"     "+name);
            if ((k1%50)==0) {
                System.out.println(k1 + "/" + list2.size());
            }
            if (excel.checkName(name)){
                assetList.add(name);

                continue;
            }else{
                //System.out.println(name);
            }
            try {
                OrderBook orderBook2 = client.getOrderBook(name, 1);
                if (orderBook2.getBids().size()==0) continue;
            }catch (Exception e){
                k1--;
                new Problem(label, "Internet problem:"+o + "/" + list2.size()).problemInternet();
                continue;
            }



            assetList.add(name);
        }
        binanceAPI.setAssetList(assetList);
        List<String> list = binanceAPI.getAssetList();

        System.out.println(binanceAPI.getAssetList().toString());

        int[] massiv = {1, 2, 3, 4, 5, 12, 24, 24*2, 24*3, 24*4, 24*5};
        String[] massiv_str = {"1H", "2H", "3H", "4H", "5H", "12H", "1D", "2D", "3D", "4D", "5D"};

        System.out.println("Старт "+0+"/"+list.size()+"");

        try {
            int ii = 1;
            /*for (String name: list){
                excel.writeRowCell(ii,1,name+"", ExcelFormat.STRING, null);
            }*/
            for (int jj = 0; jj < massiv.length; jj++) {
                excel.writeRowCell(1, jj + 2, massiv_str[jj], ExcelFormat.STRING, null);
            }

            for(int k2=0; k2<list.size(); k2++){
                String name = list.get(k2);
                int i = k2+2;
                updateProgress(k2, list.size());
                final int o = k2;
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        label.setText("Writing: "+name+"\t\t\t"+o + "/" + list.size());
                    }
                });
                //String name = tickerPrice.getSymbol();
                try {
                    List<Candlestick> candlestickIntervalList = client.getCandlestickBars(name, CandlestickInterval.HOURLY);


                    excel.writeRowCell(i, 1, name + "", ExcelFormat.STRING, null);
                    Double close = Double.parseDouble(candlestickIntervalList.get(candlestickIntervalList.size() - 1).getClose());

                    for (int j = 0; j < massiv.length; j++) {
                        int z_mass = massiv[j];
                        try {
                            Double open_1H = Double.parseDouble(candlestickIntervalList.get(candlestickIntervalList.size() - z_mass).getOpen());
                            //excel.writeRowCell(1, j + 2, massiv_str[j], ExcelFormat.STRING, null);
                            excel.writeRowCell(i, j + 2, (100 * ((close - open_1H) / open_1H)) + "", ExcelFormat.DOUBLE, "0.0");
                        } catch (Exception e) {
                        }
                    }
                }catch (Exception e){
                    k2--;
                    new Problem(label, "Internet problem:"+o + "/" + list.size()).problemInternet();

                    continue;
                }

                //System.out.println(i);
                if ((i%50)==0) {
                    excel.writeIntoExcel(file);
                    System.out.println("Запись "+i +"/"+list.size()+"");
                }
            }


            //excel.writeRowCell(1,2,"1004.00000001", ExcelFormat.DOUBLE, null);
            //excel.writeRowCell(2,2,"50", ExcelFormat.STRING, null);
            //excel.writeRowCell(3,2,"300", ExcelFormat.INT, "0.01");

            excel.writeIntoExcel("C:\\Users\\artem\\Desktop\\jjj\\writeBinance.xls");
            System.out.println("Запись "+list.size() +"/"+list.size()+"");
            excel.closeStreem();



        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return null;
    }
}
