package com.texoit.goldenraspberryawards.service.movie;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.texoit.goldenraspberryawards.domain.movie.Movie;
import com.texoit.goldenraspberryawards.domain.movie.MovieRepository;
import com.texoit.goldenraspberryawards.service.BaseCrudService;


@Service
public class MovieService extends BaseCrudService<UUID, Movie, MovieRepository> {	
	
}
