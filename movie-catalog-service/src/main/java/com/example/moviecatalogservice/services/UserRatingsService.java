package com.example.moviecatalogservice.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.moviecatalogservice.models.Rating;
import com.example.moviecatalogservice.models.UserRatings;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class UserRatingsService {
	
	@Autowired
	private RestTemplate rs;

	@HystrixCommand(fallbackMethod = "getFallbackUserRatings")
	public UserRatings getUserRatings(String userId) {
		return rs.getForObject("http://ratings-data-service/ratingsdata/users/" + userId, UserRatings.class);
	}
	

	public UserRatings getFallbackUserRatings(String userId) {
		UserRatings userRating = new UserRatings();
		userRating.setUserId(userId);
		userRating.setRatings(Arrays.asList(
				new Rating("0",0)
				));
		return userRating;
	}

}
