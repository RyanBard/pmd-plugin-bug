package org.apache.maven.pmd.bug.common;

import java.util.Optional;

public class GenericsInferredParam<T> {

    public Optional<T> getOptionalOf(T value) {
        return Optional.empty();
    }

}
