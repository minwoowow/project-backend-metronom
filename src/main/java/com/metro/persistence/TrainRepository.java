package com.metro.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.metro.domain.Train;

public interface TrainRepository extends JpaRepository<Train, Integer> {

}
