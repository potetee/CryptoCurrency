package com.example.demo.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.example.demo.Utils.CONST;
import com.example.demo.Utils.SettingProperties;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.AttributeValueUpdate;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.UpdateItemRequest;
import software.amazon.awssdk.services.dynamodb.model.UpdateItemResponse;


import javax.management.Attribute;


@Service
public class DynamoDBAccess {
	private final SettingProperties settingProperties;

	public DynamoDBAccess(SettingProperties settingProperties){
		this.settingProperties = settingProperties;
	}


	public void updateAsset(Map<String, String> updateAssetMap,String cryptoKind) {

		//make update key
		HashMap<String,AttributeValue> item_key = new HashMap<>();
		AttributeValue attributevalue = AttributeValue.builder()
				.s(CONST.CRYPTO_TABLE_KEY.getConst())
				.build();
		item_key.put(CONST.CRYPTO_PARTITION_KEY.getConst(), attributevalue);

		HashMap<String, AttributeValueUpdate> update_values = new HashMap<>();
		//set the cash
		AttributeValue cashValue = AttributeValue.builder().s(updateAssetMap.get(CONST.CASH.getConst())).build();
		AttributeValueUpdate cashParsedValue = AttributeValueUpdate.builder().value(cashValue).build();
		update_values.put(CONST.CASH.getConst(), cashParsedValue);

		//set update each value
		for (String key : updateAssetMap.keySet()) {
			if (cryptoKind.equals(key)) {
				AttributeValue updateValue = AttributeValue.builder().s(updateAssetMap.get(key)).build();
				AttributeValueUpdate attributeUpdate = AttributeValueUpdate.builder().value(updateValue).build();
				update_values.put(cryptoKind, attributeUpdate);
			}
		}

		UpdateItemRequest request = UpdateItemRequest.builder()
				.tableName(CONST.DYNAMODB_TABLE.getConst())
				.key(item_key)
				.attributeUpdates(update_values)
				.build();


		// access the dynamodb
		DynamoDbClient ddb  = makeDynamoDbClient();
		try{
			UpdateItemResponse response = ddb.updateItem(request);
		}catch (Exception e){
			System.err.println(e.getMessage());
		}finally {
			ddb.close();
		}
	}



	
	public Map<String,String> getAsset(){
		DynamoDbClient ddb = makeDynamoDbClient();
		HashMap<String,AttributeValue> keyToGet = new HashMap<>();
		keyToGet.put(CONST.CRYPTO_PARTITION_KEY.getConst(), AttributeValue.builder()
				.s(CONST.CRYPTO_TABLE_KEY.getConst())
				.build());
		GetItemRequest request = GetItemRequest.builder()
				.key(keyToGet)
				.tableName(CONST.DYNAMODB_TABLE.getConst())
				.build();
		Map<String,AttributeValue> item = ddb.getItem(request).item();
		Map<String,String> convertedMap = item.keySet().stream()
		.filter(key -> !CONST.CRYPTO_PARTITION_KEY.getConst().equals(key))
		.collect(Collectors.toMap(key ->key, key ->item.get(key).s()));
		ddb.close();
		
		return convertedMap;
	}


	
	private DynamoDbClient makeDynamoDbClient() {
		StaticCredentialsProvider credentialsProvider = StaticCredentialsProvider
				.create(AwsBasicCredentials.create(settingProperties.getAccessKeyId(), settingProperties.getSecretAccessKey()));
		return DynamoDbClient
				.builder()
				.credentialsProvider(credentialsProvider)
				.region(settingProperties.getRegion()).
						build();
		
	}



}
