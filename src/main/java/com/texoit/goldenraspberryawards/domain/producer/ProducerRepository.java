package com.texoit.goldenraspberryawards.domain.producer;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.texoit.goldenraspberryawards.domain.BaseRepository;

public interface ProducerRepository extends BaseRepository<Producer, UUID> {
	
	public Iterable<Producer> findByNameIgnoreCase(String name);

	
	@Query(value = "SELECT p.* "
			+ "FROM Producer p  "
			+ "INNER JOIN Movie_Producer mp ON p.ID = mp.producer_id "
			+ "INNER JOIN Movie m ON m.ID = MP.movie_id "
			+ "WHERE "
			+ "m.winner = TRUE "			
			+ "GROUP BY p.name "			
			+ "HAVING COUNT(m.ID) > 1 ", nativeQuery = true)
	public List<Producer> findProducersWithMoreThanOneAward();
	 
	@Query(value ="SELECT m.year "
			+ "FROM Producer p  "
			+ "INNER JOIN Movie_Producer mp ON p.ID = mp.producer_id "
			+ "INNER JOIN Movie m ON m.ID = MP.movie_id "
			+ "WHERE "
			+ "p.id = :id and "
			+ "m.winner = TRUE "
			+ "ORDER BY m.year", nativeQuery = true)
	public List<Integer> findYearOfProducerMovies(@Param("id") String id);
}
