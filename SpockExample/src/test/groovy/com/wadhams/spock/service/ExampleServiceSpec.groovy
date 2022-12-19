package com.wadhams.spock.service

import spock.lang.Specification

class ExampleServiceSpec extends Specification {
	
	def "actual string is reversed"() {
		given:
			ExampleService service = new ExampleService()
		
		when:
			String result = service.reverse('robert')
			
		then:
			result == 'trebor'
	}

	def "null reverse() returns null"() {
		given:
			ExampleService service = new ExampleService()
		
		when:
			String result = service.reverse(null)
			
		then:
			result == null
	}

	def "blank reverse() returns blank"() {
		given:
			ExampleService service = new ExampleService()
		
		when:
			String result = service.reverse('')
			
		then:
			result == ''
	}

	
	
	
}
