package com.example.demo.service;

import com.example.demo.Utils.CONST;
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
    void getCryptoCurrencyPrice() {
        Map<String,String> mockMap = new HashMap<>();
        mockMap.put("symbol","BTC");
        mockMap.put("bid","100");
        List innerList = new ArrayList<>();
        innerList.add(mockMap);


        Map outerMap = new HashMap();
        outerMap.put("data",innerList);
        doReturn(outerMap).when(restTemplate).getForObject(CONST.CRYPTO_PRICE_URL.getConst(),Map.class);
        HashMap<String,String> returnMap = getCryptoService.getCryptoCurrencyPrice();
        System.out.println(returnMap);

    }
}