package com.github.fabioyudi.matchers;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.util.List;

public class PrimeiroElementoMatcher extends TypeSafeMatcher<List<Integer>>{
    @Override
    protected boolean matchesSafely(List<Integer> integers) {
        if(integers.size() > 0){
            return integers.get(0) == 2;
        }
        return  false;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("Lista que começa com 2");

    }
}
