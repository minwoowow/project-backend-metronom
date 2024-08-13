package com.metro.domain;

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
@Table(name = "station")
public class Station {
	
	@Id
	private Integer stationId;
	private String stationName;
	@Column(length = 10)
	private String lineId;
	private boolean transferable;
	private Double longitude;
	private Double latitude;
}