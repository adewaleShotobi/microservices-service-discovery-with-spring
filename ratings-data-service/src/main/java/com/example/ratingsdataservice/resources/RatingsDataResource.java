package com.example.ratingsdataservice.resources;

import java.util.Arrays;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ratingsdataservice.models.Rating;
import com.example.ratingsdataservice.models.UserRatings;

@RestController
@RequestMapping("/ratingsdata")
public class RatingsDataResource {
	
	@RequestMapping("/{movieId}")
	public Rating getRating(@PathVariable String movieId){
		return new Rating(movieId,4);
	}
	
	
	@RequestMapping("/users/{userId}")
	public UserRatings getUserRatings(@PathVariable String userId){
		UserRatings userRatings = new UserRatings();
		userRatings.setUserId(userId);
		userRatings.setRatings(Arrays.asList(
				new Rating("200",7),
				new Rating("240",8)
		));
		return userRatings;
	}
}
