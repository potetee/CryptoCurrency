package com.example.demo.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.demo.controller.CryptoController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.example.demo.Utils.CONST;

@Service
public class GetCryptoService {
    @Autowired
    RestTemplate restTemplate;

	Logger logger = LoggerFactory.getLogger(GetCryptoService.class);
    
    public HashMap<String,String> getCryptoCurrencyPrice() {
		Map response = new HashMap();
    	try{
			response = restTemplate.getForObject(CONST.CRYPTO_PRICE_URL.getConst(), Map.class);
		}catch (RestClientException e){
    		logger.warn("Error:When fetching data from GMO");
		}

    	List<Map<String,String>> cryptos = (List<Map<String, String>>) response.get(CONST.MAP_DATA_KEY.getConst());
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
		if(returnMap.size() == 0){
			logger.warn("Error:the response does not have crypto info");
		}

		return returnMap;
    }
}
