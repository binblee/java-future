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
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = JavaFutureApplication.class)
public class Java8FeaturesTests {

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

	@Test
	public void testStream(){
		List<String> strings = Arrays.asList("bbb","aaa","ccc","aaa1");

		strings.stream().filter((s) -> s.startsWith("aa"))
			.forEach(System.out::println);

		strings.stream().sorted()
			.forEach(System.out::println);

		strings.stream().map(String::toUpperCase)
			.sorted()
			.forEach(System.out::println);

		boolean matched = strings.stream().allMatch((s) -> s.startsWith("a"));
		Assert.assertEquals(false, matched);

		matched = strings.stream().anyMatch((s)->s.startsWith("a"));
		Assert.assertEquals(true, matched);

		matched = strings.stream().noneMatch((s)->s.startsWith("a"));
		Assert.assertEquals(false, matched);

		long countOfStringsStartWithA = strings.stream()
				.filter((s)->s.startsWith("a"))
				.count();
		Assert.assertEquals(2, countOfStringsStartWithA);

		Optional<String> reducedStrings = strings.stream()
				.sorted()
				.reduce((s1,s2) -> s1 + ":" + s2);

		reducedStrings.ifPresent(System.out::println);
	}

	@Test
	public void testParallelStream(){
		List<String> strings = Arrays.asList("1111","33","00","55");
		strings.parallelStream().sorted().forEach(System.out::println);
	}

}
