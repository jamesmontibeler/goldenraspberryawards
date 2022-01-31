package com.texoit.goldenraspberryawards.resource.movie;

import java.util.UUID;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.texoit.goldenraspberryawards.domain.movie.Movie;
import com.texoit.goldenraspberryawards.resource.BaseCrudResource;
import com.texoit.goldenraspberryawards.service.movie.MovieService;


@RestController
@RequestMapping(value = "/movies")
public class MovieResource extends BaseCrudResource<UUID, Movie, MovieService> {
	
	public MovieResource() {		
		setFindByIdCacheMaxAgeInMinutes(5);
	}
	
}
