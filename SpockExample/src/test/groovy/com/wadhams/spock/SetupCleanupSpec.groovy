package com.wadhams.spock

import spock.lang.Specification

class SetupCleanupSpec extends Specification {
	int input1
	static int input2
	
	//methods run once before any tests and once after all tests have completed
	def setupSpec() {
		println("###in setup spec!")
		input2 = 2	//initialize
	}
	
	def cleanupSpec() {
		println("###in cleanup spec!")
		input2 = 0	//reset, but unnecessary
	}
	
	//methods run before and after each test
	def setup() {
		println(">>>in test setup!")
		input1 = 10	//initialize
	}
	
	def cleanup() {
		println(">>>in test cleanup!")
		input1 = 0	//reset, but unnecessary
	}
	
	def "input1 plus input2 should equal twelve" () {
		expect:
			input1 + input2 == 12
	}
	
	def "input1 minus input2 should equal eight" () {
		expect:
			input1 - input2 == 8
	}
}
