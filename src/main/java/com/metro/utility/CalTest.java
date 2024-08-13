package com.metro.utility;

import java.time.Duration;
import java.time.LocalTime;

public class CalTest {
	
	public static void main(String[] args) {
		
	LocalTime a = LocalTime.of(5, 8);
	LocalTime b = LocalTime.of(6, 14);
	
	Duration du = Duration.between(a, b);
	
	String time = String.valueOf(du.toMinutes());
	
	System.out.println(du.toMinutes());
	System.out.println(time);
	System.out.println(a);
	
	for(int i = 0; i < 10; i+=2)
		System.out.println(i);
	
	
	}

}
