package com.metro.dto;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public interface ArrivalTimeDTO {
	
	@JsonFormat(pattern = "HH:mm")
	LocalTime getArrivalTime();
}
