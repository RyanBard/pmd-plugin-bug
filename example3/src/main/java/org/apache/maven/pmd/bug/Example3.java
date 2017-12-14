package org.apache.maven.pmd.bug;

import org.apache.maven.pmd.bug.common.GenericFromParam;

import java.util.Optional;

public class Example3 {

    public static <T> GenericFromParam<T> newInstance() {

        // This should be a PMD failure
        if (true) System.out.println("This no-brace if-statement should show up in pmd output");

        return new GenericFromParam<T>() {
            @Override
            public Optional<T> getOptionalOf(T value) {
                return Optional.of(value);
            }
        };
    }

    public static void main(String[] args) {
        GenericFromParam<String> gfp1 = newInstance();
        Optional<String> s1 = gfp1.getOptionalOf("abc");

        GenericFromParam<String> gfp2 = new GenericFromParam<>();
        Optional<String> s2 = gfp2.getOptionalOf("def");

        System.out.println("s1: " + s1.orElse("not-set"));
        System.out.println("s2: " + s2.orElse("not-set"));
    }

}
