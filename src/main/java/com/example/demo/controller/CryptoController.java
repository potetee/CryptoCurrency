package com.example.demo.controller;


import java.util.HashMap;
import java.util.Map;

import com.example.demo.service.DealCryptoCurrency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Utils.CONST;
import com.example.demo.service.DynamoDBAccess;
import com.example.demo.service.GetCryptoService;

@CrossOrigin
@RestController
public class CryptoController {
	private final DynamoDBAccess dynamoDBAccess;
	private final GetCryptoService getCryptoService;
	private final DealCryptoCurrency dealCryptoCurrency;

	Logger logger = LoggerFactory.getLogger(CryptoController.class);

	public  CryptoController(DynamoDBAccess dynamoDBAccess,
							 GetCryptoService getCryptoService,
							 DealCryptoCurrency dealCryptoCurrency){
		this.dynamoDBAccess = dynamoDBAccess;
		this.getCryptoService = getCryptoService;
		this.dealCryptoCurrency = dealCryptoCurrency;

	}

	
	@PutMapping("/deal/{AskOrBid}/{kind}/{amount}")
	public Map<String,Map<String,String>> deal(@PathVariable String AskOrBid,@PathVariable String kind,
			@PathVariable String amount){

		Map<String,Map<String,String>> returnMap = dealCryptoCurrency.dealCurrency(AskOrBid,
				kind,
				amount);
		if(returnMap.get(CONST.MAP_MESSAGE_KEY.getConst()).size() == 0){
			return getStringMapMap();
		}

		return returnMap;
	}

	@GetMapping("/asset")
	public Map<String,Map<String,String>> getAsset() {
		logger.trace("A TRACE Message");
		logger.debug("A DEBUG Message");
		logger.info("An INFO Message");
		logger.warn("A WARN Message");
		logger.error("An ERROR Message");
		return getStringMapMap();
	}

	private Map<String, Map<String, String>> getStringMapMap() {
		Map<String,Map<String,String>> responseMap = new HashMap<>();
		Map<String,String> amountMap = dynamoDBAccess.getAsset();
		HashMap<String,String> priceMap =   getCryptoService.getCryptoCurrencyPrice();
		responseMap.put(CONST.MAP_AMOUNT_KEY.getConst(), amountMap);
		responseMap.put(CONST.MAP_PRICE_KEY.getConst(), priceMap);
		return responseMap;
	}

}
