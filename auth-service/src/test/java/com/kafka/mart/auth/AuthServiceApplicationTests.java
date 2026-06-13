package com.kafka.mart.auth;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class AuthServiceApplicationTests {

	@Test
	void main_shouldStartApplication() {

		assertDoesNotThrow(
				() -> AuthServiceApplication.main(
						new String[]{}
				)
		);
	}
}