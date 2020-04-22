package com.guru.login.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.guru.login.model.Lead;

@Repository
public interface LeadRepository extends JpaRepository<Lead, Long> {
	 /**
     * DB contains table 'movies_genres:(movie_id, genre_id)
     * Spring Data JPA automatically finds all Genres that are related to 'movie'
     *
     * @param movie         movie object, contains 'movie_id'
     * @param pageable      Abstract interface for pagination information from PageRequest
     * @return              Page<T> Object with Genre Objects
     */
    Page<Lead> findAll(Pageable pageable);
}