package com.example.demo.Utils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import software.amazon.awssdk.regions.Region;

@Component
@ConfigurationProperties(prefix="app")
public class SettingProperties {
	//AWS DynamoDB setting
	@Setter
	@Getter
	private String accessKeyId = "AKIA2DEUKKIEOW5EJBUQ";
	@Setter
	@Getter
	private String secretAccessKey = "Fasr1WGuqxdVI72CDB80mqCFYTO04tGtqmFACTG7";
	@Setter
	@Getter
	private Region region = Region.US_EAST_2;
}
