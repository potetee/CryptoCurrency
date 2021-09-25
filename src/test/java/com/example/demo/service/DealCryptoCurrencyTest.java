package com.example.demo.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DealCryptoCurrencyTest {

    @InjectMocks
    private DealCryptoCurrency dealCryptoCurrency;
    @Mock
    private DynamoDBAccess dynamoDBAccess;
    @Mock
    private GetCryptoService getCryptoService;

    @Test
    public void checkInputPath00() {
        Method method = makeCheckInputPathMethod();
        Map<String, Map<String, String>> returnedMap = null;
        try {
            returnedMap = (Map<String, Map<String, String>>) method.invoke(dealCryptoCurrency,"ask","BTC","1");
        } catch (IllegalAccessException e) {
            fail();
        } catch (InvocationTargetException e) {
            fail();
        }
        Map<String,String> actualMap = returnedMap.get("message");
        assertEquals(actualMap.size(),0);
    }
    @Test
    public void checkInputPath01() {
        Method method = makeCheckInputPathMethod();
        Map<String, Map<String, String>> returnedMap = null;
        try {
            returnedMap = (Map<String, Map<String, String>>) method.invoke(dealCryptoCurrency,"asks","BTC","1");
        } catch (IllegalAccessException e) {
            fail();
        } catch (InvocationTargetException e) {
            fail();
        }
        Map<String,String> actualMap = returnedMap.get("message");
        assertEquals(actualMap.size(),1);
        assertEquals(actualMap.get("NOT VALID DEAL"),"SET BID OR ASK");
    }
    @Test
    public void checkInputPath02() {
        Method method = makeCheckInputPathMethod();
        Map<String, Map<String, String>> returnedMap = null;
        try {
            returnedMap = (Map<String, Map<String, String>>) method.invoke(dealCryptoCurrency,"ask","BT","1");
        } catch (IllegalAccessException e) {
            fail();
        } catch (InvocationTargetException e) {
            fail();
        }
        Map<String,String> actualMap = returnedMap.get("message");
        assertEquals(actualMap.size(),1);
        assertEquals(actualMap.get("NOT VALID CRYPTO"),"SET VALID CRYPTO");
    }
    @Test
    public void checkInputPath03() {
        Method method = makeCheckInputPathMethod();
        Map<String, Map<String, String>> returnedMap = null;
        try {
            returnedMap = (Map<String, Map<String, String>>) method.invoke(dealCryptoCurrency,"ask","BTC","100000");
        } catch (IllegalAccessException e) {
            fail();
        } catch (InvocationTargetException e) {
            fail();
        }
        Map<String,String> actualMap = returnedMap.get("message");
        assertEquals(actualMap.size(),1);
        assertEquals(actualMap.get("NOT VALID AMOUNT"),"SET 1,10,OR 100");
    }
    @Test
    public void checkInputPath04() {
        Method method = makeCheckInputPathMethod();
        Map<String, Map<String, String>> returnedMap = null;
        try {
            returnedMap = (Map<String, Map<String, String>>) method.invoke(dealCryptoCurrency,"bask","BdTC","100000");
        } catch (IllegalAccessException e) {
            fail();
        } catch (InvocationTargetException e) {
            fail();
        }
        Map<String,String> actualMap = returnedMap.get("message");
        assertEquals(actualMap.size(),3);
        assertEquals(actualMap.get("NOT VALID DEAL"),"SET BID OR ASK");
        assertEquals(actualMap.get("NOT VALID CRYPTO"),"SET VALID CRYPTO");
        assertEquals(actualMap.get("NOT VALID AMOUNT"),"SET 1,10,OR 100");
    }
    @Test
    public void checkInputPath05() {
        Method method = makeCheckInputPathMethod();
        Map<String, Map<String, String>> returnedMap = null;
        try {
            returnedMap = (Map<String, Map<String, String>>) method.invoke(dealCryptoCurrency,"asks","BTCD","100");
        } catch (IllegalAccessException e) {
            fail();
        } catch (InvocationTargetException e) {
            fail();
        }
        Map<String,String> actualMap = returnedMap.get("message");
        assertEquals(actualMap.size(),2);

    }

    @Test
    public void checkAssetTest01(){
        //input
        Map<String,String> inputMap = new HashMap<>();
        String inputKind = "BTC";
        inputMap.put(inputKind,"1");
        String inputAskOrBid = "ask";
        String inputAmount = "10";

        //target method
        Method method = makeCheckAssetMethod();;
        method.setAccessible(true);
        boolean actual = true;
        try {
             actual = (boolean)method.invoke(dealCryptoCurrency,inputMap,inputAskOrBid,inputKind,inputAmount);
        } catch (IllegalAccessException e) {
            fail();
        } catch (InvocationTargetException e) {
            fail();
        }
        assertEquals(actual,true);
    }

    @Test
    public void checkAssetTest02(){
        //input
        Map<String,String> inputMap = new HashMap<>();
        String inputKind = "BTC";
        inputMap.put(inputKind,"1");
        String inputAskOrBid = "ask";
        String inputAmount = "0";

        //target method
        Method method = makeCheckAssetMethod();;
        method.setAccessible(true);
        boolean actual = true;
        try {
            actual = (boolean)method.invoke(dealCryptoCurrency,inputMap,inputAskOrBid,inputKind,inputAmount);
        } catch (IllegalAccessException e) {
            fail();
        } catch (InvocationTargetException e) {
            fail();
        }
        assertEquals(actual,true);
    }



    private Method makeCheckInputPathMethod(){
        Method method = null;
        try {
            method = DealCryptoCurrency.class.getDeclaredMethod("checkInputPath",
                    String.class,String.class,String.class);
        } catch (NoSuchMethodException e) {
            fail();
        }
        method.setAccessible(true);
        return method;
    }
    private Method makeCheckAssetMethod(){
        Method method = null;
        try {
            method = DealCryptoCurrency.class.getDeclaredMethod("checkAsset",
                    Map.class,String.class,String.class,String.class);
        } catch (NoSuchMethodException e) {
            fail();
        }
        method.setAccessible(true);
        return method;
    }

    @Test
    public void checkAssetTest03(){
        //input
        Map<String,String> inputMap = new HashMap<>();
        String inputKind = "BTC";
        inputMap.put(inputKind,"1");
        String inputAskOrBid = "bid";
        String inputAmount = "10";

        //target method
        Method method = makeCheckAssetMethod();;
        method.setAccessible(true);
        boolean actual = true;
        try {
            actual = (boolean)method.invoke(dealCryptoCurrency,inputMap,inputAskOrBid,inputKind,inputAmount);
        } catch (IllegalAccessException e) {
            fail();
        } catch (InvocationTargetException e) {
            fail();
        }
        assertEquals(false,actual);
    }

    @Test
    public void checkAssetTest04(){
        //input
        Map<String,String> inputMap = new HashMap<>();
        String inputKind = "BTC";
        inputMap.put(inputKind,"10");
        String inputAskOrBid = "bid";
        String inputAmount = "5";

        //target method
        Method method = makeCheckAssetMethod();;
        method.setAccessible(true);
        boolean actual = true;
        try {
            actual = (boolean)method.invoke(dealCryptoCurrency,inputMap,inputAskOrBid,inputKind,inputAmount);
        } catch (IllegalAccessException e) {
            fail();
        } catch (InvocationTargetException e) {
            fail();
        }
        assertEquals(true,actual);
    }

    @Test
    public void checkCashTest00(){
        Map<String,String> priceMap = new HashMap<>();
        priceMap.put("BTC","100");
        doReturn(priceMap).when(getCryptoService).getCryptoCurrencyPrice();
        Map<String,String> currentAssetMap = new HashMap<>();
        currentAssetMap.put("BTC","1");
        currentAssetMap.put("investment-capability","1000");
        when(dynamoDBAccess.getAsset()).thenReturn(currentAssetMap);
        dealCryptoCurrency.dealCurrency("ask","BTC","1");

    }


}