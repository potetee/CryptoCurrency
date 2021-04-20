package com.example.demo.service;


import com.example.demo.Utils.CONST;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DealCryptoCurrency {

    private final DynamoDBAccess dynamoDBAccess;
    private final GetCryptoService getCryptoService;

    DealCryptoCurrency(DynamoDBAccess dynamoDBAccess,GetCryptoService getCryptoService){
        this.dynamoDBAccess = dynamoDBAccess;
        this.getCryptoService = getCryptoService;
    }

    public Map<String, Map<String,String>>  dealCurrency(String askOrBid, String kind, String amount){

        Map<String,Map<String,String>> returnMap = checkInputPath(askOrBid,kind,amount);
        HashMap<String,String> message = new HashMap<>();

        if(returnMap.get(CONST.MAP_MESSAGE_KEY.getConst()).size() == 0   ) {
            //when the request path is valid
            Map<String,String> currentAssetMap;
            currentAssetMap = dynamoDBAccess.getAsset();
            boolean assetCheck = checkAsset(currentAssetMap, askOrBid,kind,amount);

            // if the deal is ask and cash is not enough ,then return money error
            if(CONST.ASK.getConst().equals(askOrBid)){
                boolean cashCheck = checkCash(currentAssetMap,kind,amount);
                if(!cashCheck){
                    // when the cash is not enough
                    message.put(CONST.MAP_ERROR_NOT_ENOUGH_CASH_KEY.getConst(),CONST.MAP_ERROR_NOT_ENOUGH_CASH_MESSAGE.getConst());
                    returnMap.put(CONST.MAP_MESSAGE_KEY.getConst(),message);
                    return returnMap;
                }
            }

            if(assetCheck){
                // when the deal can be done
                int targetAsset = Integer.parseInt(currentAssetMap.get(kind));
                int parsedAmount = Integer.parseInt(amount);

                // calculate the cash after deal
                HashMap<String,String> priceMap = getCryptoService.getCryptoCurrencyPrice();
                String price = priceMap.get(kind);
                if(price.contains(".")){
                    int dotPlace = priceMap.get(kind).indexOf(".");
                    price = priceMap.get(kind).substring(0,dotPlace);
                }
                int cryptPrice = Integer.parseInt(price);
                int currentCash = Integer.parseInt(currentAssetMap.get(CONST.CASH.getConst()));
                int dealCash = cryptPrice*Integer.parseInt(amount);

                if(CONST.ASK.getConst().equals(askOrBid)){
                    //set the after deal crypto
                    targetAsset+= parsedAmount;
                    currentAssetMap.put(CONST.CASH.getConst(),String.valueOf(currentCash-dealCash));
                }else{
                    targetAsset-= parsedAmount;
                    currentAssetMap.put(CONST.CASH.getConst(),String.valueOf(currentCash+dealCash));
                }

                currentAssetMap.put(kind,String.valueOf(targetAsset));
                dynamoDBAccess.updateAsset(currentAssetMap,kind);

            }else{
                // when the cannot be done
                message.put(CONST.MAP_ERROR_DEAL_KEY.getConst(),CONST.MAP_ERROR_ASSET_MESSAGE.getConst());
                returnMap.put(CONST.MAP_MESSAGE_KEY.getConst(),message);
            }
        }
        return returnMap;
    }

    private boolean checkCash(Map<String, String> currentAssetMap, String kind, String amount) {
        HashMap<String,String> priceMap =  getCryptoService.getCryptoCurrencyPrice();
        int currentCash = Integer.parseInt(currentAssetMap.get(CONST.CASH.getConst()));
        String price = priceMap.get(kind);
        if(price.contains(".")){
            int dotPlace = priceMap.get(kind).indexOf(".");
            price = priceMap.get(kind).substring(0,dotPlace);
        }
        int targetPrice = Integer.parseInt(price);
        int restCash = currentCash-targetPrice*Integer.parseInt(amount);
        boolean cashCheckResult = true;
        if(restCash < 0){
            cashCheckResult = false;
        }
        return  cashCheckResult;
    }

    private boolean checkAsset(Map<String, String> currentAssetMap, String askOrBid, String kind, String amount) {
        boolean checkedResult = true;

        int selectedAssetAmount = Integer.parseInt(currentAssetMap.get(kind));
        int parsedAmount = Integer.parseInt(amount);

        //calculate the asset with deal request
        if(CONST.ASK.getConst().equals(askOrBid)){
            selectedAssetAmount += parsedAmount;
        }else if (CONST.BID.getConst().equals(askOrBid)){
            selectedAssetAmount -= parsedAmount;
        }

        //judge capability of deal
        if(selectedAssetAmount < 0){
            checkedResult = false;
        }

        return checkedResult;
    }


    private Map<String, Map<String, String>> checkInputPath(String askOrBid, String kind, String amount) {
        Map<String, Map<String, String>> resultMessageMap = new HashMap<>();
        List<String> cryptoList = Arrays.asList(CONST.BTC.getConst(), CONST.ETH.getConst(), CONST.XRP.getConst());
        List<String> askOrBidList = Arrays.asList(CONST.BID.getConst(), CONST.ASK.getConst());
        List<String> amountList = Arrays.asList("1","10","100");
        HashMap<String,String> checkedMap = new HashMap<>();
        if(Objects.isNull(askOrBid) || !askOrBidList.contains(askOrBid)) {
            checkedMap.put(CONST.MAP_ERROR_DEAL_KEY.getConst(), CONST.MAP_ERROR_DEAL_MESSAGE.getConst());
        }
        if(Objects.isNull(kind) || !cryptoList.contains(kind)) {
            checkedMap.put(CONST.MAP_ERROR_CRYPTO_KEY.getConst(), CONST.MAP_ERROR_CRYPTO_MESSAGE.getConst());
        }
        if(Objects.isNull(amount) || !amountList.contains(amount)) {
            checkedMap.put(CONST.MAP_ERROR_AMOUNT_KEY.getConst(), CONST.MAP_ERROR_AMOUNT_MESSAGE.getConst());
        }

        resultMessageMap.put(CONST.MAP_MESSAGE_KEY.getConst(), checkedMap);

        return resultMessageMap;
    }




}
