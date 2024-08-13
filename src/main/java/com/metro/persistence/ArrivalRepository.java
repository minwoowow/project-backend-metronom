package com.metro.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.metro.domain.Arrival;
import com.metro.dto.ArrivalInfoDTO;
import com.metro.dto.ArrivalTimeDTO;

public interface ArrivalRepository extends JpaRepository<Arrival, Integer> {
	
	@Query(value = "SELECT arrival_time FROM arrival WHERE station_id= ?1 AND arrival_time > curtime() ORDER BY arrival_time ASC LIMIT 2", nativeQuery = true)
	List<ArrivalTimeDTO> getArrivalTime(Integer stationId);
	
	@Query(value = "SELECT station_name, A.station_id, arrival_time "
				+ "FROM station S, arrival A "
				+ "WHERE A.train_id = ("
					+ "SELECT A.train_id FROM arrival A, train T WHERE A.station_id = ?1 "
					+ "AND end_station_id = ?3 AND A.train_id = T.train_id "
					+ "AND arrival_time > ?4 ORDER BY arrival_time ASC LIMIT 1) "
				+ "AND A.station_id IN (?1, ?2) AND A.station_id = S.station_id", nativeQuery = true)					
	List<ArrivalInfoDTO> getArrivalInfo(Integer dId, Integer aId, Integer lId, String time);

}