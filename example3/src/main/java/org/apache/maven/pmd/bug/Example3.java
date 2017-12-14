package org.apache.maven.pmd.bug;

import org.apache.maven.pmd.bug.common.GenericsInferredParam;

import java.util.Optional;

public class Example3 {

    public static <T> GenericsInferredParam<T> newInstance() {

        // This should be a PMD failure
        if (true) System.out.println("This no-brace if-statement should show up in pmd output");

        return new GenericsInferredParam<T>() {
            @Override
            public Optional<T> getOptionalOf(T value) {
                return Optional.of(value);
            }
        };
    }

    public static void main(String[] args) {
        GenericsInferredParam<String> gfp1 = newInstance();
        Optional<String> s1 = gfp1.getOptionalOf("abc");

        GenericsInferredParam<String> gfp2 = new GenericsInferredParam<>();
        Optional<String> s2 = gfp2.getOptionalOf("def");

        System.out.println("s1: " + s1.orElse("not-set"));
        System.out.println("s2: " + s2.orElse("not-set"));
    }

}
