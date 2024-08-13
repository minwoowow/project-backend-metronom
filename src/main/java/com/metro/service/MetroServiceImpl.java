package com.metro.service;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.metro.domain.Station;
import com.metro.dto.ArrivalInfoDTO;
import com.metro.dto.ArrivalTimeDTO;
import com.metro.dto.LocationDTO;
import com.metro.persistence.ArrivalRepository;
import com.metro.persistence.StationRepository;
import com.metro.utility.path.ShortestPath;
import com.metro.utility.path.TransferStations;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MetroServiceImpl implements MetroService {
	
	private final StationRepository stationRepo;
	private final ArrivalRepository arrivalRepo;
	
	@Override
	public LocationDTO getLocation(Integer id) {
		
		Station station = stationRepo.findById(id).get();
		
		LocationDTO locationDTO = LocationDTO.builder()
							.longitude(station.getLongitude())
							.latitude(station.getLatitude())
							.build();
		return locationDTO;
	}	
	
	@Override
	public List<ArrivalTimeDTO> getArrivalTime(Integer stationId) {
		
		List<ArrivalTimeDTO> list = arrivalRepo.getArrivalTime(stationId);
		
		return list;
	}
	
	@Override
	public Map<String, Object> getArrivalInfo(Integer sId, Integer eId) {
		List<ArrivalInfoDTO> infoList = new ArrayList<>(); 
		
		ShortestPath sp = new ShortestPath();
		List<Integer> stationList = sp.getStationList(sId, eId);
		
		TransferStations ts = new TransferStations();
		List<Integer> transferStations = ts.findTransferStation(stationList);
		
		List<Integer> resultPath = new ArrayList<>();
		resultPath.addAll(Arrays.asList(sId, eId));
		resultPath.addAll(1, transferStations);
		
		Map<String, Integer> map = new HashMap<>() {
			private static final long serialVersionUID = 1L;
		{
			put("119", 2);
			put("219", 2);
			put("123", 3);
			put("305", 3);
			put("125", 6);
			put("402", 6);
			put("208", 3);
			put("301", 3);
			put("233", 3);
			put("313", 3);
			put("309", 4);
			put("401", 4);
		}};
		
		for(int i = 0; i < resultPath.size(); i += 2) {
			int start = resultPath.get(i);
			int end = resultPath.get(i + 1);
			int last = 0;
			String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")); 
					
			switch (start / 100) {
			case 0:
			case 1:
				last = ((start - end) < 0) ? 134 : 95;
				break;
			case 2:
				last = ((start - end) < 0) ? 243 : 201;				
				break;
			case 3:
				last = ((start - end) < 0) ? 317 : 301;
				break;
			case 4:
				last = ((start - end) < 0) ? 414 : 401;
				break;
			default:
				break;
			}
			
			if(i == 0) {
				infoList.addAll(arrivalRepo.getArrivalInfo(start, end, last, time));
			} else {
				time = String.valueOf(infoList.get(i - 1).getArrivalTime().plusMinutes(map.get(String.valueOf(start))));
				
				infoList.addAll(arrivalRepo.getArrivalInfo(start, end, last, time));
			}
		}
		
		Duration duration = Duration.between(infoList.get(0).getArrivalTime(), infoList.get(infoList.size() - 1).getArrivalTime());
		String totalTime = String.valueOf(duration.toMinutes());
		
		Map<String, Object> resultMap = new HashMap<>();
		
		resultMap.put("infoList", infoList);
		resultMap.put("totalTime", totalTime);

		return resultMap;
	}
}
