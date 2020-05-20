package com.example.moviecatalogservice.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.moviecatalogservice.models.CatalogItem;
import com.example.moviecatalogservice.models.UserRatings;
import com.example.moviecatalogservice.services.MovieInfoService;
import com.example.moviecatalogservice.services.UserRatingsService;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
	
	@Autowired
	UserRatingsService userRatingsService;
	
	@Autowired
	MovieInfoService movieInfoService;
	
	@RequestMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable String userId){
		
		//get all rated movies id
		UserRatings userRatings = userRatingsService.getUserRatings(userId);
		
		//for each movie ID, call movie info service
		return userRatings.getRatings().stream().map(rating-> movieInfoService.getMovieInfo(rating))
		.collect(Collectors.toList());
		
	}

}
