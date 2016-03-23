package com.example;

import com.example.java8.Converter;
import com.example.java8.Formula;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

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

	@Test
	public void testFunctionalInterfaces(){
		Converter<String, Integer> converter1 = new Converter<String, Integer>() {
			@Override
			public Integer convert(String from) {
				return Integer.valueOf(from);
			}
		};

		Integer result = converter1.convert("1");
		Assert.assertEquals(1, result.intValue());

		Converter<String, Integer> converter2 = (from) -> Integer.valueOf(from);

		result = converter2.convert("2");
		Assert.assertEquals(2,result.intValue());
	}

	@Test
	public void testLambdaScope(){
		int num = 1;
		Converter<String,Integer> converter = (s) -> new Integer(num);

		Assert.assertEquals(num, converter.convert("1").intValue());
	}

	@Test
	public void testPredicate(){
		Predicate<String> lengthGTEZero = (s) -> s.length() > 0;

		Assert.assertEquals(true, lengthGTEZero.test("a"));
		Assert.assertEquals(false, lengthGTEZero.negate().test("a"));
	}

	@Test
	public void testFunction(){
		Function<String, Integer> toInteger = Integer::valueOf;
		Function<String, String> backToString = toInteger.andThen(String::valueOf);
		Assert.assertEquals("0", backToString.apply("0"));
	}

}
