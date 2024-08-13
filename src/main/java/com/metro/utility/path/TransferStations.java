package com.metro.utility.path;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TransferStations {
	
	public List<Integer> findTransferStation(List<Integer> path) {
		List<Integer> foundTransfers = new ArrayList<>();
		
		for(int i = 0; i < path.size() - 1; i++) {
			int isTransfer = path.get(i) - path.get(i + 1);
			if(isTransfer != 1 & isTransfer != -1) {
				foundTransfers.addAll(Arrays.asList(path.get(i), path.get(i + 1)));				
			}		
		}
		return foundTransfers;
	}	
}
