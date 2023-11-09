package nota.symbols.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public class OccurrencesTask implements Callable<Map<Character, Integer>> {
    private final String text;
    private final int start;
    private final int end;

    public OccurrencesTask(String text, int start, int end) {
        this.text = text;
        this.start = start;
        this.end = end;
    }

    @Override
    public Map<Character, Integer> call() {

        Map<Character, Integer> occurrencesMap = new HashMap<>();

        for (int i = start; i <= end; i++) {
            occurrencesMap.compute(text.charAt(i), (k, v) -> v == null ? 1 : v + 1);
        }

        return occurrencesMap;
    }
}
