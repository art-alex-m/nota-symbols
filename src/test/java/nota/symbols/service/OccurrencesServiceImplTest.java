package nota.symbols.service;

import nota.symbols.dto.Entry;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class OccurrencesServiceImplTest {

    @ParameterizedTest
    @MethodSource
    void compute(String text, List<Entry> expected) {
        OccurrencesService sut = new OccurrencesServiceImpl();

        List<Entry> result = sut.compute(text);

        assertIterableEquals(expected, result);
    }

    public static Stream<Arguments> compute() {
        return Stream.of(
                Arguments.of("cbbaaa", List.of(
                        new Entry('a', 3),
                        new Entry('b', 2),
                        new Entry('c', 1)
                )),
                Arguments.of("2bbc1aa1a", List.of(
                        new Entry('a', 3),
                        new Entry('1', 2),
                        new Entry('b', 2),
                        new Entry('2', 1),
                        new Entry('c', 1)
                ))
        );
    }
}