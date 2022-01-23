package com.rbc.stocktickermanager;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
class StocktickermanagerApplicationTests {

	@Test
	void contextLoads() {
		assertThat("A", is("A"));
	}

}
