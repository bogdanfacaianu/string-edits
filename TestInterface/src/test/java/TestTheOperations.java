import com.github.liblevenshtein.transducer.Candidate;
import com.github.liblevenshtein.transducer.ITransducer;
import com.string.edits.StringEditsApplication;
import com.string.edits.configuration.StringEditsConfiguration;
import com.string.edits.domain.Language;
import com.string.edits.domain.TermQuery;
import com.string.edits.service.DictionaryOperations;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {StringEditsConfiguration.class, StringEditsApplication.class})
public class TestTheOperations {

    @Autowired
    private DictionaryOperations dictionaryOperations;

    @Test
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

    @SpringBootApplication
    static class TestConfiguration {
    }
}