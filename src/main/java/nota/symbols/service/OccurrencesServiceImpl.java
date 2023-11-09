package nota.symbols.service;

import nota.symbols.dto.Entry;

import java.util.List;
import java.util.stream.Collectors;

public class OccurrencesServiceImpl implements OccurrencesService {
    @Override
    public List<Entry> compute(String text) {
        return text.chars()
                .mapToObj(k -> (char) k)
                .collect(Collectors.groupingBy(k -> k, Collectors.summingInt(v -> 1)))
                .entrySet().stream()
                .map(pair -> new Entry(pair.getKey(), pair.getValue()))
                .toList();
    }
}
