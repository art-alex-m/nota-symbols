package nota.symbols.service;

import nota.symbols.dto.Entry;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class OccurrencesServiceThreadImplTest {

    @ParameterizedTest
    @MethodSource("nota.symbols.service.OccurrencesServiceImplTest#compute")
    void compute(String text, List<Entry> expected) {
        ExecutorService executorService = Executors.newWorkStealingPool(2);
        OccurrencesService sut = new OccurrencesServiceThreadImpl(executorService);

        List<Entry> result = sut.compute(text);

        assertIterableEquals(expected, result);
    }

    @Test
    void computeWithLargeString() throws IOException {
        String resourceName = "occurrencesServiceThreadImplResource.txt";
        String text = new String(Objects.requireNonNull(getClass().getResourceAsStream(resourceName)).readAllBytes());
        List<Entry> expected = List.of(
                new Entry('5', 200360),
                new Entry('d', 200333),
                new Entry('0', 200027),
                new Entry('i', 199767),
                new Entry('a', 199514)
        );
        ExecutorService executorService = Executors.newWorkStealingPool(5);
        OccurrencesService sut = new OccurrencesServiceThreadImpl(executorService).setSymbolsPerTask(10000);

        List<Entry> result = sut.compute(text);

        assertIterableEquals(expected, result);
    }
}