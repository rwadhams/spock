package com.wadhams.spock.dto

import groovy.transform.ToString

@ToString (includeNames = true)
class CityInfo {
	String city
	int population
	
	int getCleanlinessScore() {
		throw new RuntimeException("method not implemented")
	}
}
