package com.wadhams.spock.service

class PaymentGateway {
	def makePayment(BigDecimal amt) {
		println 'You should never se this message as we are mocking this class and method call'
		//real code here...
	}
}
