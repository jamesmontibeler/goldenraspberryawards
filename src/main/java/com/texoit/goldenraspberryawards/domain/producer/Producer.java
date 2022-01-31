package com.texoit.goldenraspberryawards.domain.producer;

import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.texoit.goldenraspberryawards.domain.BaseEntity;
import com.texoit.goldenraspberryawards.domain.movie.Movie;

@Entity
public class Producer implements BaseEntity<UUID> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Type(type="uuid-char")
	@Column(name="id", columnDefinition = "VARCHAR(255)", insertable = false, updatable = false, nullable = false)
	private UUID id;
	
	@NotNull(message="The field 'Name' of the producer is empty.")
	private String name;
	
	@ManyToMany(mappedBy = "producers", fetch = FetchType.LAZY)
	@JsonIgnore
	private Set<Movie> movies;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Movie> getMovies() {
		return movies;
	}

	public void setMovies(Set<Movie> movies) {
		this.movies = movies;
	}

	
}
