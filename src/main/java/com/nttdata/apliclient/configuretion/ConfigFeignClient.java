package com.nttdata.apliclient.configuretion;

import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Configuration
public class ConfigFeignClient {
	@Bean
	@ConditionalOnMissingBean
	public HttpMessageConverters messageConverters(ObjectProvider<HttpMessageConverter<?>> converters) {
		
		
		return new HttpMessageConverters(converters.orderedStream().collect(Collectors.toList()));
		//return new HttpMessageConverters(converters.orderedStream().collect(Collectors.toList()));
		//return  Flux.just(new HttpMessageConverters(converters.orderedStream().collect(Collectors.toList())));
	}; 
	
	

}
