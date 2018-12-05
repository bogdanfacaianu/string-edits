package com.string.edits.configuration;

import com.string.edits.domain.Language;
import com.string.edits.persistence.repository.LanguageRepository;
import com.string.edits.repository.DefaultLanguageRepository;
import com.string.edits.service.DictionaryOperations;
import com.string.edits.service.DictionaryService;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.string.edits")
public class StringEditsConfiguration {

//    @Bean
//    public LanguageRepository languageRepository(Map<String, Language> languageRepository) {
//        return new DefaultLanguageRepository(languageRepository);
//    }
//
//    @Bean
//    public DictionaryService dictionaryService() {
//        return new DictionaryService();
//    }
//
//    @Bean
//    public DictionaryOperations dictionaryOperations() {
//        return new DictionaryOperations();
//    }
}
