package com.uki.uber;




import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.domain.geo.GeoReference;
import org.springframework.security.core.parameters.P;

import java.awt.*;


@SpringBootApplication
public class UberApplication {

	public static void main(String[] args) {
		SpringApplication.run(UberApplication.class, args);
	}

//	@Bean
//	RedisConnectionFactory jedisConnectionFactory(){
//		return new LettuceConnectionFactory();
//	}
//	@Bean
//	public RedisTemplate<String,String> redisTemplate(){
//		RedisTemplate<String, String > redisTemplate = new RedisTemplate<>();
//		redisTemplate.setConnectionFactory(jedisConnectionFactory());
//		return redisTemplate;
//	}




//	@Bean
//	public ApplicationRunner runner(RedisTemplate<String, String> template){
//
//		return args -> {
//			Point point = new Point(44.267136,30.833549);
//			System.out.println(template.opsForGeo().add("testDriver",point, "shouldWorka"));
//			Distance distance = new Distance(5000, Metrics.KILOMETERS);
//			Circle circle = new Circle(point, distance);
//			System.out.println(template.opsForGeo().search("drivers",circle));
//			GeoReference<String> geoReference = new GeoReference.GeoCoordinateReference<>(44.267136, 30.833549);
//			System.out.println(template.opsForGeo().search("drivers",geoReference,distance, RedisGeoCommands.GeoSearchCommandArgs.newGeoSearchArgs().includeCoordinates()));
//
//		};
//
//
//	}


}
