package nota.symbols.controller;

import nota.symbols.dto.Entry;
import nota.symbols.dto.Error;
import nota.symbols.service.OccurrencesService;
import nota.symbols.service.OccurrencesTaskExecutionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/occurrence", produces = MediaType.APPLICATION_JSON_VALUE)
public class OccurrencesController {

    private final OccurrencesService occurrencesService;

    public OccurrencesController(OccurrencesService occurrencesService) {
        this.occurrencesService = occurrencesService;
    }

    @PostMapping
    public List<Entry> compute(@RequestBody String text) {
        return occurrencesService.compute(text);
    }

    @ExceptionHandler(HttpMessageConversionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<Error> handleEmptyRequestException(HttpMessageConversionException ex) {
        return List.of(new Error("text", ex.getLocalizedMessage()));
    }

    @ExceptionHandler(OccurrencesTaskExecutionException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public List<Error> handleEmptyRequestException(RuntimeException ex) {
        return List.of(new Error("text", ex.getLocalizedMessage()));
    }
}
