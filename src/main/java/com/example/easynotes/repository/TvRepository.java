package com.example.easynotes.repository;

import com.example.easynotes.model.Tv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TvRepository extends JpaRepository<Tv, Long> {
}
