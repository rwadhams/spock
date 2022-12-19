package com.wadhams.spock.service

import org.apache.logging.log4j.Logger
import org.apache.logging.log4j.LogManager

class ExampleService {
	def reverse(String input) {
		if (input != null) {
			return input.reverse()
		}
		return null
	}
}
