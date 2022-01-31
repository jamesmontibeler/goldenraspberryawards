package com.texoit.goldenraspberryawards.domain.movie;

import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

import com.texoit.goldenraspberryawards.domain.BaseEntity;
import com.texoit.goldenraspberryawards.domain.producer.Producer;

@Entity
public class Movie implements BaseEntity<UUID> {
	
	public Movie() {
		winner = false;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Type(type="uuid-char")
	@Column(name="id", columnDefinition = "VARCHAR(255)", insertable = false, updatable = false, nullable = false)
	private UUID id;
	
	@NotNull(message="The field 'Year' of the movie is empty.")
	private Integer year;
	
	@NotNull(message="The field 'Title' of the movie is empty.")
	private String title;
	
	@NotNull(message="The field 'Studios' of the movie is empty.")
	private String studios;	

	@NotNull(message="The field 'producers' of the movie is empty.")
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			  name = "movie_producer", 
			  joinColumns = @JoinColumn(name = "movie_id"), 
			  inverseJoinColumns = @JoinColumn(name = "producer_id"))
	private Set<Producer> producers;	
	
	private Boolean winner;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	public String getStudios() {
		return studios;
	}

	public void setStudios(String studios) {
		this.studios = studios;
	}

	public Set<Producer> getProducers() {
		return producers;
	}

	public void setProducers(Set<Producer> producers) {
		this.producers = producers;
	}

	public Boolean isWinner() {
		return winner;
	}

	public void setIsWinner(Boolean winner) {
		this.winner = winner;
	}	
	
	
	
}
