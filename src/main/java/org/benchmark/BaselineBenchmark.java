package org.benchmark;

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
public class BaselineBenchmark {

    SampleDto dto;

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
    }

    @Benchmark
    public void useNullChecks4Level(Blackhole blackhole) {
        SampleDto dto = this.dto;

        if (dto.getSampleDto() == null) {
            return;
        }
        dto = dto.getSampleDto(); // 2th level

        if (dto.getSampleDto() == null) {
            return;
        }
        dto = dto.getSampleDto(); // 3th level

        if (dto.getSampleDto() == null) {
            return;
        }
        dto = dto.getSampleDto(); // 4th level

        blackhole.consume(dto);
    }

    @Benchmark
    public void emptyMethod(Blackhole blackhole) {
        blackhole.consume(dto);
    }

    @Benchmark
    public void produceOptional(Blackhole blackhole) {
        blackhole.consume(Optional.ofNullable(dto));
    }

}
