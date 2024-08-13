package com.metro.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.metro.domain.Station;

public interface StationRepository extends JpaRepository<Station, Integer> {

}
