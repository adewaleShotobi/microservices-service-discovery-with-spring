package com.example.moviecatalogservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.moviecatalogservice.models.CatalogItem;
import com.example.moviecatalogservice.models.Movie;
import com.example.moviecatalogservice.models.Rating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Service
public class MovieInfoService {
	
	@Autowired
	private RestTemplate rs;

//	Hystrix Config properties
//	@HystrixCommand(fallbackMethod = "getFallbackMovieInfo",
//			commandProperties = {
//					@HystrixProperty(name ="execution.isolation.thread.timeoutInMilliseconds",value = "2000"),
//					@HystrixProperty(name ="circuitBreaker.requestVolumeThreshold",value = "5"),
//					@HystrixProperty(name ="circuitBreaker.errorThresholdPercentage",value = "50"),
//					@HystrixProperty(name ="circuitBreaker.sleepWindowInMilliseconds",value = "5000")
//			})

	@HystrixCommand(fallbackMethod = "getFallbackMovieInfo")
	public CatalogItem getMovieInfo(Rating rating) {
		Movie mov = rs.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);	
		return new CatalogItem(mov.getName(), mov.getOverview(), rating.getRating());
	}
	
	public CatalogItem getFallbackMovieInfo(Rating rating) {
		return new CatalogItem("Movie not found", "No Overview", rating.getRating());
	}

}
