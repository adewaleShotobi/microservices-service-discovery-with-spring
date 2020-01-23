package com.example.moviecatalogservice.resources;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.moviecatalogservice.models.CatalogItem;
import com.example.moviecatalogservice.models.Movie;
import com.example.moviecatalogservice.models.Rating;
import com.example.moviecatalogservice.models.UserRatings;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
	
	@Autowired
	private RestTemplate rs;
	
	@RequestMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable String userId){
		
		//get all rated movies id
		UserRatings userRatings = rs.getForObject("http://localhost:8083/ratingsdata/users/" + userId, UserRatings.class);
		
		//for each movie ID, call movie info service
		return userRatings.getRatings().stream().map(rating-> {
			Movie mov = rs.getForObject("http://localhost:8089/movies/" + rating.getMovieId(), Movie.class);	
			return new CatalogItem(mov.getName(), "Desc", rating.getRating());
		})
		.collect(Collectors.toList());
		
//		return Collections.singletonList(
//				new CatalogItem("SEAL Team","test",4)
//				);
	}

}
