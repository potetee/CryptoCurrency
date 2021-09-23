package com.example.demo.service;

import com.example.demo.Utils.CONST;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
class GetCryptoServiceTest {

    @InjectMocks
    GetCryptoService getCryptoService;

    @Mock
    RestTemplate restTemplate;


    @Test
    void getCryptoCurrencyPrice00() {
        Map<String,String> mockBTCMap = new HashMap<>();
        mockBTCMap.put("symbol","BTC");
        mockBTCMap.put("bid","100");
        Map<String,String> mockETHMap = new HashMap<>();
        mockETHMap.put("symbol","ETH");
        mockETHMap.put("bid","200");
        Map<String,String> mockXRPMap = new HashMap<>();
        mockXRPMap.put("symbol","XRP");
        mockXRPMap.put("bid","300");

        List innerList = new ArrayList<>();
        innerList.add(mockBTCMap);
        innerList.add(mockETHMap);
        innerList.add(mockXRPMap);


        Map outerMap = new HashMap();
        outerMap.put("data",innerList);
        doReturn(outerMap).when(restTemplate).getForObject(CONST.CRYPTO_PRICE_URL.getConst(),Map.class);
        HashMap<String,String> returnMap = getCryptoService.getCryptoCurrencyPrice();
        assertEquals(returnMap.get("BTC"),"100");
        assertEquals(returnMap.get("ETH"),"200");
        assertEquals(returnMap.get("XRP"),"300");
    }

    @Test
    void getCryptoCurrencyPrice01(){
        doThrow(new RestClientException("rest error")).when(restTemplate).getForObject(CONST.CRYPTO_PRICE_URL.getConst(),Map.class);
        System.out.println("this test to check when the rest show error");
        try{
            getCryptoService.getCryptoCurrencyPrice();
        }catch (Exception e){
            System.out.println("↑ target message");
        }
    }

    @Test
    void getCryptoCurrencyPrice02(){
        Map<String,String> mockBTCMap = new HashMap<>();
        mockBTCMap.put("symbol","B");
        mockBTCMap.put("bid","100");
        Map<String,String> mockETHMap = new HashMap<>();
        mockETHMap.put("symbol","E");
        mockETHMap.put("bid","200");
        Map<String,String> mockXRPMap = new HashMap<>();
        mockXRPMap.put("symbol","X");
        mockXRPMap.put("bid","300");

        List innerList = new ArrayList<>();
        innerList.add(mockBTCMap);
        innerList.add(mockETHMap);
        innerList.add(mockXRPMap);


        Map outerMap = new HashMap();
        outerMap.put("data",innerList);
        doReturn(outerMap).when(restTemplate).getForObject(CONST.CRYPTO_PRICE_URL.getConst(),Map.class);
        System.out.println("when the return map is 0");
        getCryptoService.getCryptoCurrencyPrice();
    }

}