package com.metro.dto;

import java.time.LocalTime;

public interface ArrivalInfoDTO {
	
	String getStationName();
	Integer getStationId();
	LocalTime getArrivalTime();

}
