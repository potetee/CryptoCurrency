package com.example.demo.Utils;

public enum CONST {
	
	DYNAMODB_TABLE("CryptoCurrency"),
	CRYPTO_PARTITION_KEY("Crypto"),
	CRYPTO_TABLE_KEY("site"),
	BTC("BTC"),
	ETH("ETH"),
	BCH("BCH"),
	LTC("LTC"),
	XRP("XRP"),
	CASH("investment-capability"),
	ASK("ask"),
	BID("bid"),
	CRYPTO_KIND("symbol"),
	PRICE("bid"),
	CRYPTO_PRICE_URL("https://api.coin.z.com/public/v1/ticker"),
	MAP_DATA_KEY("data"),
	MAP_AMOUNT_KEY("amount"),
	MAP_MESSAGE_KEY("message"),
	MAP_PRICE_KEY("price"),
	MAP_ERROR_CRYPTO_KEY("NOT VALID CRYPTO"),
	MAP_ERROR_CRYPTO_MESSAGE("SET VALID CRYPTO"),
	MAP_ERROR_DEAL_KEY("NOT VALID DEAL"),
	MAP_ERROR_DEAL_MESSAGE("SET BID OR ASK"),
	MAP_ERROR_AMOUNT_KEY("NOT VALID AMOUNT"),
	MAP_ERROR_AMOUNT_MESSAGE("SET 1,10,OR 100"),
	MAP_ERROR_ASSET_KEY("THIS DEAL CAN BE DONE "),
	MAP_ERROR_ASSET_MESSAGE("CHECK YOUR ASSET AND PRICE"),
	MAP_ERROR_NOT_ENOUGH_CASH_KEY("NOT ENOUGH CASH"),
	MAP_ERROR_NOT_ENOUGH_CASH_MESSAGE("You do not have enough cash");

	
	private final String value;
	
	private CONST(String value) {
		this.value = value;
	}
	public String getConst() {
		return this.value;
	}

	

}
