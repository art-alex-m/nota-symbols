package nota.symbols.service;

import nota.symbols.dto.Entry;

import java.util.List;

public interface OccurrencesService {
    List<Entry> compute(String text);
}
