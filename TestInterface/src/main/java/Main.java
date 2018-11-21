import com.github.liblevenshtein.transducer.Candidate;
import com.github.liblevenshtein.transducer.ITransducer;
import com.string.edits.HelloWorld;
import com.string.edits.domain.Language;
import com.string.edits.domain.TermQuery;
import com.string.edits.service.DictionaryBuilder;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class Main {

    @Test
    public void testRepo() {
        Set<String> dictionary = new HashSet<String>();
        dictionary.add("GAMBOL");
        dictionary.add("MASINA");
        dictionary.add("STAFIDA");

        Language language = new Language("testLanguage1", dictionary);
        Optional<ITransducer<Candidate>> candidateITransducer = HelloWorld.buildTransducerForLanguage(language);
        if (candidateITransducer.isPresent()) {
            ITransducer<Candidate> transducer = candidateITransducer.get();
            TermQuery termQuery = HelloWorld.returnResults(transducer, "STAFICLA");
            System.out.println(termQuery);
        }
    }

}