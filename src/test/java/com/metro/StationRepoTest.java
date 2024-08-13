package com.metro;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.metro.dto.ArrivalInfoDTO;
import com.metro.persistence.ArrivalRepository;
import com.metro.persistence.StationRepository;
import com.metro.service.MetroService;

@SpringBootTest
public class StationRepoTest {
	
	@Autowired
	private StationRepository stationRepo;
	
	@Autowired
	private ArrivalRepository arrivalRepo;
	
	@Autowired
	private MetroService metroService;
	
//	@Test
//	public void getStation() {
//		
//		Optional<Station> st = stationRepo.findById(95);
//		
//		System.out.println(st);		
//	}
	
//	@Test
//	public void getArrivalTest() {
//		
//		List<ArrivalTimeDTO> list = arrivalRepo.getArrivalTime(115);
//		
//		for(ArrivalTimeDTO t : list) {
//				System.out.println("arrival Time : " + t);
//			}
//	}
	
	@Test
	public void getArrivalInfoTest() {
		Map<String, Object> map = metroService.getArrivalInfo(101, 112);
		
		@SuppressWarnings("unchecked")
		List<ArrivalInfoDTO> list = (List<ArrivalInfoDTO>) map.get("infoList");
		String time = (String) map.get("totalTime");
		
		for(ArrivalInfoDTO a : list)
			System.out.println(a);
		
		System.out.println(time);
	}
	
//	@Test
//	public void arrivalRepoTest() {
//		
//		System.out.println("테스트중-----------------------------------");
//		List<ArrivalInfoDTO> list = arrivalRepo.getArrivalInfo(95, 119, 134, "17:30:00");
//		
//		System.out.println("출력결과----------------------------");
//		for(ArrivalInfoDTO d : list) {
//			System.out.println(d);
//		}		
//	}
}
