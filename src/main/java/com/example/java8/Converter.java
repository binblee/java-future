package com.example.java8;

/**
 * Created by libin on 3/22/16.
 */

// Annotation is optional
@FunctionalInterface
public interface Converter<F,T> {
    T convert(F from);
}
