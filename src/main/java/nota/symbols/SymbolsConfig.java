package nota.symbols;

import nota.symbols.service.OccurrencesService;
import nota.symbols.service.OccurrencesServiceImpl;
import nota.symbols.service.OccurrencesServiceThreadImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class SymbolsConfig {

    @Value("${nota.symbols.max-threads:64}")
    private int maxThreads;

    @Value("${nota.symbols.symbols-per-task:1000}")
    private int symbolsPerTask;

    @Bean
    @ConditionalOnProperty(prefix = "nota", name = "symbols.symbols-use-threads", havingValue = "0")
    public OccurrencesService occurrencesService() {
        return new OccurrencesServiceImpl();
    }

    @Bean
    @ConditionalOnProperty(prefix = "nota", name = "symbols.symbols-use-threads", havingValue = "1")
    public OccurrencesService occurrencesThreadService(ExecutorService executorService) {
        return new OccurrencesServiceThreadImpl(executorService)
                .setSymbolsPerTask(symbolsPerTask);
    }

    @Bean
    @ConditionalOnProperty(prefix = "nota", name = "symbols.symbols-use-threads", havingValue = "1")
    public ExecutorService executorService() {
        return Executors.newWorkStealingPool(maxThreads);
    }
}
