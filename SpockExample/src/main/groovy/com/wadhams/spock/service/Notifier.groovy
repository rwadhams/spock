package com.wadhams.spock.service

class Notifier {
	void notify(String message) {
		println 'You should never se this message as we are mocking this class and method call'
		//real code here...
	}
}
