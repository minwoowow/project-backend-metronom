package com.metro.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.metro.domain.Line;

public interface LineRepository extends JpaRepository<Line, String> {

}
