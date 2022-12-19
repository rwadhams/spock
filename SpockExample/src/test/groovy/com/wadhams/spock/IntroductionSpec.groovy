package com.wadhams.spock

import com.wadhams.spock.dto.CityInfo
import com.wadhams.spock.service.Notifier
import com.wadhams.spock.service.PaymentGateway

import spock.lang.Specification
import spock.lang.Unroll

class IntroductionSpec extends Specification {
//
// STRUCTURE OF A SPOCK TEST
//

	def "one plus one should equal two"() {
		expect:
			1 + 1 == 2
	}

/*
 BLOCKS:
 1.	Setup (Aliased by Given) - Here we perform any setup needed before a test is run. 
 This is an implicit block, with code not in any block at all becoming part of it.
 2.	When - This is where we provide a stimulus to what is under test. 
 In other words, where we invoke our method under test.
 3.	Then - This is where the assertions belong. 
 In Spock, these are evaluated as plain boolean assertions.
 4.	Expect - This is a way of performing our stimulus and assertion within the same block. 
 Depending on what we find more expressive, we may or may not choose to use this block.
 5.	Cleanup - Here we tear down any test dependency resources which would otherwise be left behind. 
 For example, we might want to remove any files from the file system or remove test data written to a database.
 */	

	def "two plus two should equal four"() {
		given: "two integers required for addition test"
			int left = 2
			int right = 2

		when: "add two numbers (stimulus)"
			int result = left + right

		then: "we should get this result (response)"
			result == 4
	}

	def "Should be able to remove from list"() {
		given:
			def list = [1, 2, 3, 4]

		when:
			list.remove(0)

		then:
			list == [2, 3, 4]
	}

	def "Should get an index out of bounds when removing a non-existent item"() {
		given:
			def list = [1, 2, 3, 4]

		when:
			list.remove(20)

		then:
			thrown(IndexOutOfBoundsException)
			list.size() == 4
	}

//
// DATA DRIVEN TESTING
//

//	@Unroll -doesn't appear to do anything with this version of Spock.

	def "numbers to the power of two"(int a, int b, int c) {
		expect:
			Math.pow(a, b) == c
	
			where:
			a | b | c
			1 | 2 | 1
			2 | 2 | 4
			3 | 2 | 9
	}

	def "sample parameterized test with list data type"() {
		when:
			def actualCount = input1.size()
			then:
				actualCount == expectedCount
			where:
			input1                                      ||expectedCount
			["hello","world","happy","programming"]     ||4
			["spock","data-driven","testing"]           ||3
	}
	
	def "result of adding #a & #b should be #expectedResult"() {
		expect:
			a + b == expectedResult
	
			where:
			a 		| b 	|| expectedResult
			10     	|15     ||25 
			-4     	|6      ||2 
			-32    	|12     ||-20
	}

//
// MOCKING
//

	def "creating a mocking and using it" () {
		//option 1
		//PaymentGateway paymentGateway = Mock()

		//option 2
		def paymentGateway = Mock(PaymentGateway)

		when:
			def result = paymentGateway.makePayment(12.99)

		then:
			result == null
	}

	def "stubbing method with argument" () {
		def paymentGateway = Mock(PaymentGateway)

		given:
		    paymentGateway.makePayment(20) >> true
		
		when:
		    def result = paymentGateway.makePayment(20)
		
		then:
		    result == true
	}

	def "stubbing method with any argument" () {
		def paymentGateway = Mock(PaymentGateway)

		given:
			paymentGateway.makePayment(_) >> true
		
		when:
			def result = paymentGateway.makePayment(0)
		
		then:
			result == true
	}

	def "stubbing method returns multiple results" () {
		def paymentGateway = Mock(PaymentGateway)

		given:
			paymentGateway.makePayment(_) >>> [true, true, false, true]
		
		when:
			def result1 = paymentGateway.makePayment(0)
			def result2 = paymentGateway.makePayment(1)
			def result3 = paymentGateway.makePayment(2)
			def result4 = paymentGateway.makePayment(3)
			
		then:
			result1 == true
			result2 == true
			result3 == false
			result4 == true
	}

//	VERIFICATION
//	Another thing we might want to do with mocks is assert that various methods were called on them with expected parameters.
//	In other words, we ought to verify interactions with our mocks.
//	
//	A typical use case for verification would be if a method on our mock had a void return type.
//	In this case, by there being no result for us to operate on, there's no inferred behavior for us to test via the method under test.
//	Generally, if something was returned, then the method under test could operate on it, and it's the result of that operation would be what we assert.
	
	def "Should verify notify was called"() {
		given:
			Notifier notifier = Mock()
	
		when:
			notifier.notify('foo')
	
		// Spock is leveraging Groovy operator overloading again. 
		// By multiplying our mocks method call by one, we are saying how many times we expect it to have been called.
		then:
			1 * notifier.notify('foo')
	}
	
	def "Should verify notify wasn't called with a particular argument"() {
		given:
			Notifier notifier = Mock()
	
		when:
			notifier.notify('foo')
	
		then:
			1 * notifier.notify(!'bar')
	}

	def "list size is 3" () {
		given:
			List cityInfoList = buildCityInfoList()
			
		expect:
			cityInfoList.size() == 3
	}
	
	//spread operator and every() closure
	def "assert multiple elements of list" () {
		given:
			List cityInfoList = buildCityInfoList()
			
		expect:
			cityInfoList*.city == ['Brisbane','Sydney','Melbourne']
		
		and:
			cityInfoList.population.every() {
				it > 80
			}
	}

	def "cleanliness score throws runtime exception with message - method not implemented" () {
		given:
			CityInfo cityInfo = new CityInfo(city : 'Hobart', population : 90)
		
		when:
			cityInfo.cleanlinessScore()
			
		then:
			def e = thrown(RuntimeException)
			e.message == "method not implemented"
	}

	//helper method
	List buildCityInfoList() {
		List cityInfoList = []
		
		cityInfoList << new CityInfo(city : 'Brisbane', population : 100)
		cityInfoList << new CityInfo(city : 'Sydney', population : 500)
		cityInfoList << new CityInfo(city : 'Melbourne', population : 400)
		
		return cityInfoList
	}
}
