package nota.symbols.service;

import nota.symbols.dto.Entry;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OccurrencesServiceThreadImpl implements OccurrencesService {

    private final static int TASK_RESULT_WAIT = 50;
    private final static
    Function<Future<Map<Character, Integer>>, Stream<Map.Entry<Character, Integer>>> flatMapper = (future) -> {
        try {
            return future.get().entrySet().stream();
        } catch (InterruptedException | ExecutionException ex) {
            throw new OccurrencesTaskExecutionException(ex);
        }
    };
    private final ExecutorService executorService;

    private int symbolsPerTask = 1000;

    public OccurrencesServiceThreadImpl(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public OccurrencesService setSymbolsPerTask(int symbolsPerTask) {
        this.symbolsPerTask = symbolsPerTask;
        return this;
    }

    @Override
    public List<Entry> compute(final String text) {
        int length = text.length() - 1;
        int start = 0;
        List<Future<Map<Character, Integer>>> results = new LinkedList<>();

        while (start < length) {
            int end = Math.min(length, start + symbolsPerTask);
            results.add(executorService.submit(new OccurrencesTask(text, start, end)));
            start = end + 1;
        }

        boolean wait = true;
        while (wait) {
            wait = false;
            for (Future<Map<Character, Integer>> future : results) {
                try {
                    future.get(TASK_RESULT_WAIT, TimeUnit.MICROSECONDS);
                } catch (InterruptedException | ExecutionException e) {
                    throw new OccurrencesTaskExecutionException(e);
                } catch (TimeoutException e) {
                    wait = true;
                }
            }
        }

        return results.stream()
                .flatMap(flatMapper)
                .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.summingInt(Map.Entry::getValue)))
                .entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(e -> new Entry(e.getKey(), e.getValue()))
                .toList();
    }
}
