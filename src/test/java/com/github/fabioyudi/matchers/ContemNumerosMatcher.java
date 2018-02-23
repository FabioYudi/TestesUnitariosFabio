package com.github.fabioyudi.matchers;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.util.Arrays;
import java.util.List;

public class ContemNumerosMatcher extends TypeSafeMatcher<List<Integer>> {
    @Override
    protected boolean matchesSafely(List<Integer> integers) {
        List<Integer> compare = Arrays.asList(1,2,3,10);


        if(integers.containsAll(compare)){
            return true;
        }
        return false;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("Contem os numeros 1, 2, 3 e 10");
    }
}
