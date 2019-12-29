package org.dto;

import java.util.Optional;

public class DtoWithOptional {

    private SampleDto object;

    public DtoWithOptional(final SampleDto object) {
        this.object = object;
    }

    public Optional<SampleDto> getObject() {
        return Optional.of(object);
    }
}
