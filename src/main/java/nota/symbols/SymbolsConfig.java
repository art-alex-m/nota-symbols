package nota.symbols;

import nota.symbols.service.OccurrencesService;
import nota.symbols.service.OccurrencesServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SymbolsConfig {

    @Bean
    public OccurrencesService occurrencesService() {
        return new OccurrencesServiceImpl();
    }
}
