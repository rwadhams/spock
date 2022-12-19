package com.wadhams.spock.app

import com.wadhams.spock.service.ExampleService

import groovy.util.logging.Log4j2

@Log4j2 (value = 'logger')
class ExampleApp {
	ExampleService service = new ExampleService()
	
	static main(args) {
		logger.info 'Log4j2Example04App started...'

		ExampleApp app = new ExampleApp()
		app.execute()
		
		logger.info 'Log4j2Example04App ended.'
	}
	
	def execute() {
		logger.info 'execute()...'
		
		String input = 'robert'
		String result = service.reverse(input)
		logger.info "The reverse of $input is: $result"
	}
}
