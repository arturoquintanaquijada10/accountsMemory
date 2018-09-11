package com.sunhill.accountssunhillMemory.config;

import java.util.WeakHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.keyvalue.core.KeyValueAdapter;
import org.springframework.data.keyvalue.core.KeyValueOperations;
import org.springframework.data.keyvalue.core.KeyValueTemplate;
import org.springframework.data.map.MapKeyValueAdapter;



@Configuration
public class AppConfig {
	
	@Bean
	public KeyValueOperations keyValueTemplate() {
	    return new KeyValueTemplate(keyValueAdapter());
	}
	 
	@Bean
	public KeyValueAdapter keyValueAdapter() {
	    return new MapKeyValueAdapter(WeakHashMap.class);
	}
	
	
	
	
	
}