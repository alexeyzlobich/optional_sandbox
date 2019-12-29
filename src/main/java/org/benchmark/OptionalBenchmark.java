package org.benchmark;


import org.dto.DtoWithOptional;
import org.dto.SampleDto;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.MINUTES)
@Fork(1)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Benchmark)
public class OptionalBenchmark {

    SampleDto dto;
    SampleDto anotherDto;

    Optional<SampleDto> optionalDto;

    DtoWithOptional dtoWithOptional;

    @Setup
    public void prepare() {
        SampleDto level_1 = new SampleDto();
        SampleDto level_2 = new SampleDto();
        SampleDto level_3 = new SampleDto();
        SampleDto level_4 = new SampleDto();

        level_1.setSampleDto(level_2);
        level_2.setSampleDto(level_3);
        level_3.setSampleDto(level_4);

        dto = level_1;

        anotherDto = new SampleDto();

        optionalDto = Optional.of(dto);

        dtoWithOptional = new DtoWithOptional(level_1);
    }

    @Benchmark
    public void useOptional4Level(Blackhole blackhole) {
        Optional.ofNullable(dto)
                .map(SampleDto::getSampleDto) // to 2th level
                .map(SampleDto::getSampleDto) // to 3th level
                .map(SampleDto::getSampleDto) // to 4th level
                .ifPresent(blackhole::consume);
    }

    @Benchmark
    public void useOptional4LevelFromState(Blackhole blackhole) {
        optionalDto
                .map(SampleDto::getSampleDto) // to 2th level
                .map(SampleDto::getSampleDto) // to 3th level
                .map(SampleDto::getSampleDto) // to 4th level
                .ifPresent(blackhole::consume);
    }

    @Benchmark
    public void useOptionalInsteadOfIfElse(Blackhole blackhole) {
        blackhole.consume(Optional.ofNullable(dto)
                .orElse(anotherDto));
    }

    @Benchmark
    public void useOptional4LevelFromDto(Blackhole blackhole) {
        dtoWithOptional.getObject()
                .map(SampleDto::getSampleDto) // to 2th level
                .map(SampleDto::getSampleDto) // to 3th level
                .map(SampleDto::getSampleDto) // to 4th level
                .ifPresent(blackhole::consume);
    }

}
