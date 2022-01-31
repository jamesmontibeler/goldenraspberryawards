package com.texoit.goldenraspberryawards.service.loaddata;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.texoit.goldenraspberryawards.domain.movie.Movie;
import com.texoit.goldenraspberryawards.domain.producer.Producer;
import com.texoit.goldenraspberryawards.service.movie.MovieService;
import com.texoit.goldenraspberryawards.service.producer.ProducerService;

@Service
public class LoadDataService {
	
	private static final String DATA_FILE_CSV =  System.getenv("DATA_FILE_CSV");

	@Autowired
	protected MovieService movieService;
	
	@Autowired
	protected ProducerService producerService;

	private static final String[] COLUMNS = {"year", "title", "studios", "producers", "winner"};
	private static final String YES = "yes";
	private static final List<String> WINNER_COLUMN_VALUES = List.of(YES, "no", "");

	private final Logger logger = LoggerFactory.getLogger(getClass());

	public LoadDataService() {
	}

	@EventListener(ApplicationReadyEvent.class)
	public void loadDataFromFile() throws IOException, FileNotFoundException {

		if (Strings.isBlank(DATA_FILE_CSV)) {
			logger.info("No file to import! Environment variable DATA_FILE_CSV is empty.");
			return;
		}
			
		logger.info("Starting file import: {}", DATA_FILE_CSV);			
		
	    BufferedReader br = null;
	    String line = "";
	    String csvDivisor = ";";
	    boolean gotColumns = false;
	    long qtdMovies = 0;
	    try {
	        br = new BufferedReader(new FileReader(DATA_FILE_CSV));
	        while ((line = br.readLine()) != null) {

	        	String[] values = line.split(csvDivisor);
	        	if (!gotColumns) {
	        	  String[] columns = values;
	        	  if (!checkColumns(columns)) {
	        		  break;
	        	  }
	        	  gotColumns = true;
	        	} else {
	        		Optional<Movie> optMovie = csvToMovie(values);
					if (optMovie.isPresent()) {						
					    qtdMovies++;
					}	
	        	}	        	 
	        }	   
	    } finally {
	        if (br != null) {
	            br.close();
	        }
	    }	

	    logger.info("Qty imported movies: {}", qtdMovies);

	}

	private boolean checkColumns(String[] columns) {
		if (columns.length != COLUMNS.length) {
			logger.error("Qty of columns of the file is invalid: was expected '{}' but was found '{}'.", COLUMNS.length, columns.length);
			return false;
		}
		 
		for (int i = 0; i < columns.length; i++) {
			if (!columns[i].equals(COLUMNS[i])) {
				logger.error("Column name '{}' is invalid: was expected '{}' but was found '{}'.", columns[i], COLUMNS[i], columns[i]);
				return false;
			}
		}
		return true;
	}
	
	private Optional<Movie> csvToMovie(String[] values) {
		
		if ((COLUMNS.length > (values.length + 1)) || (COLUMNS.length < values.length)) { //it's possible for the last value did'nt exists
			logger.error("Qty of values entered is incompatible with qty. of columns: was expected '{}' but was found '{}'. Unable to merge record {}.", COLUMNS.length, values.length, movieCsvToString(values));
			return Optional.empty();
		}
		
		Movie movie = new Movie();	
		try {
			movie.setYear(Integer.valueOf(values[0]));
		} catch (Exception e) {
			logger.error("Column '{}' was not informed with numeric value. Value informed: '{}'. Unable to merge record {}", COLUMNS[0], values[0], movieCsvToString(values));
			return Optional.empty();
		}	
				
		movie.setTitle(values[1]);
		movie.setStudios(values[2]);
		
		if (values.length > 4) { //it's possible for the last value did'nt exists	
			
			if (!WINNER_COLUMN_VALUES.contains(values[4].toLowerCase())) {
				logger.error("Column '{}' was informed with invalid value: was expected [{}] but was found '{}'. Unable to merge record {}", COLUMNS[4], WINNER_COLUMN_VALUES, values[4], movieCsvToString(values));
				return Optional.empty();
			}
			
			movie.setIsWinner(values[4].toLowerCase().equals(YES));	
		} else {
			movie.setIsWinner(false);
		}		
					
		try {
			movie.setProducers(csvToProducer(values[3]));
			
			movie = movieService.save(movie);
		} catch (Exception e) {
			logger.error("{}. Unable to merge registry {}", e.getMessage(), movieCsvToString(values));
			return Optional.empty();
		}
				
		return Optional.of(movie); 
	}	
	
	private Set<Producer> csvToProducer(String names) {
		List<String> listNames = this.splitNames(names);
		Set<Producer> producers = new HashSet<>();
		for (String name : listNames) {
			if (!name.isEmpty()) {
				Iterable<Producer> found = producerService.findByNameIgnoreCase(name.trim());
				if (found.iterator().hasNext()) {
					producers.add(found.iterator().next());
				} else {				
					Producer producer = new Producer();			
					producer.setName(name.trim());
					producer = producerService.save(producer);
					producers.add(producer);
				}	
			}	
		} 
		return producers;
	}
	
	private List<String> splitNames(String names) {
		List<String> splited = new ArrayList<>();		
		for (String item : names.split(",")) {
			splited.addAll(Arrays.asList(item.split(" and ")));
		}		
		return splited;
	}

	private String movieCsvToString(String[] values) {
		Map<String, String> map = new HashMap<>();
		for (int i = 0; i < COLUMNS.length; i++) {
			map.put(COLUMNS[i], values.length > i ? values[i] : "");
		}
		return map.toString();
	}
	
	
}
