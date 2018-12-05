package com.stringedits.testrunner;

import com.github.liblevenshtein.transducer.Candidate;
import com.github.liblevenshtein.transducer.ITransducer;
import com.string.edits.domain.Language;
import com.string.edits.domain.TermQuery;
import com.string.edits.service.DictionaryOperations;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;

public class Main {

    @Autowired
    private DictionaryOperations dictionaryOperations;

    public void testRepo() {
        Set<String> dictionary = new HashSet<String>();
        dictionary.add("GAMBOL");
        dictionary.add("MASINA");
        dictionary.add("STAFIDA");

        Language language = new Language("testLanguage1", dictionary);
        Optional<ITransducer<Candidate>> candidateITransducer = dictionaryOperations.buildTransducerForLanguage(language);
        if (candidateITransducer.isPresent()) {
            ITransducer<Candidate> transducer = candidateITransducer.get();
            TermQuery termQuery = dictionaryOperations.returnResults(transducer, "STAFICLA");
            System.out.println(termQuery);
        }
    }

}