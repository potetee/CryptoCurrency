package com.example.demo.Utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import org.springframework.stereotype.Service;

@Service
public class PropertyUtil {
	private  final String INIT_FILE_PATH = "../../../application.properties";
    public String getProperty(final String key, final String defaultValue) {
    	Properties properties = new Properties();
    	try {
			properties.load(Files.newBufferedReader(Paths.get(INIT_FILE_PATH), StandardCharsets.UTF_8));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return properties.getProperty(key, defaultValue);
    }
}
