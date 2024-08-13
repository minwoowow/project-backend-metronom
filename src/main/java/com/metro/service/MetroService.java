package com.metro.service;

import java.util.List;
import java.util.Map;

import com.metro.dto.ArrivalTimeDTO;
import com.metro.dto.LocationDTO;

public interface MetroService {

	LocationDTO getLocation(Integer id);

	List<ArrivalTimeDTO> getArrivalTime(Integer stationId);

	Map<String, Object> getArrivalInfo(Integer sId, Integer eId);


}