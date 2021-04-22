package com.example.demo.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.Utils.CONST;

@Service
public class GetCryptoService {
    @Autowired
    RestTemplate restTemplate;
    
    public HashMap<String,String> getCryptoCurrencyPrice() {
    	Map response = restTemplate.getForObject(CONST.CRYPTO_PRICE_URL.getConst(), Map.class);
    	List<Map<String,String>> cryptos = (List<Map<String, String>>) response.get(CONST.MAP_DATA_KEY.getConst());
    	//test
    	List<String> cryptoList = Arrays.asList(
    			CONST.BTC.getConst(),
    			CONST.ETH.getConst(),
    			CONST.XRP.getConst());
    	
    	HashMap<String,String> returnMap = new HashMap<>();
    	for(String crypto:cryptoList) {
    		for(Map<String,String> priceMap:cryptos) {
    			
    			if(crypto.equals(priceMap.get(CONST.CRYPTO_KIND.getConst()))) {
    				returnMap.put(crypto, priceMap.get(CONST.PRICE.getConst()));
    			}
    		}
    	}
		return returnMap;
    }
}
