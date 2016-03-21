package com.example;

import com.example.java8.Formula;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = JavaFutureApplication.class)
public class JavaFutureApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Test
	public void testDefaultMethodOfInterface(){
		Formula formula = new Formula() {
			@Override
			public int calculate(int a) {
				return a+1;
			}
		};

		Assert.assertEquals(11,formula.calculate(10));
		Assert.assertEquals(10,formula.abs(10));
	}

	@Test
	public void testLambda(){
		List<String> names = Arrays.asList("peter", "anna", "mike", "xenia");

		Collections.sort(names, (String a, String b) -> {
			return b.compareTo(a);
		});
	}

	@Test
	public void testLambdaOneLiner(){
		List<String> names = Arrays.asList("peter", "anna", "mike", "xenia");

		Collections.sort(names, (a,b) -> b.compareTo(a) );
	}

}
