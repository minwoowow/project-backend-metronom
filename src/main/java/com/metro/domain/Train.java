package com.metro.domain;

import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "train")
public class Train {
	
	@Id
	private Integer trainId;
	@Column(length = 10)
	private String lineId;
	@Column(name = "start_station_id")
	private Integer startStId;
	@Column(name = "end_station_id")
	private Integer endStId;
	private Integer direction;
	private LocalTime startTime;
	private LocalTime endTime;

}
