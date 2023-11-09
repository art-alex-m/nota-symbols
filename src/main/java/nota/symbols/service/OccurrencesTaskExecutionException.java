package nota.symbols.service;

public class OccurrencesTaskExecutionException extends RuntimeException {
    public OccurrencesTaskExecutionException() {
        super();
    }

    public OccurrencesTaskExecutionException(Exception ex) {
        super(ex);
    }

    public OccurrencesTaskExecutionException(String msg) {
        super(msg);
    }
}
