package dev.vinyard.adventofcode.common;

import dev.vinyard.adventofcode.utils.FileReader;
import dev.vinyard.aoc.plugins.solution.api.Solution;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseTest<T> {

    public abstract Solution<T> getSolution();

    public abstract Stream<Arguments> testSolution();

    @ParameterizedTest
    @MethodSource
    public void testSolution(String fileName, T expected, Class<? extends Exception> expectedException, String errorMsg) {
        try {
            Assertions.assertThat(getSolution().solve(FileReader.readFileToString(fileName))).isEqualTo(expected);
        } catch (Exception e) {
            Assertions.assertThat(expectedException).withFailMessage(String.format("Unexpected exception has been thrown : %s", e)).isNotNull();
            Assertions.assertThat(e).isInstanceOf(expectedException).hasMessage(errorMsg);
        }
    }
}
