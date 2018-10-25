import com.github.liblevenshtein.transducer.Candidate;
import com.github.liblevenshtein.transducer.ITransducer;
import com.string.edits.HelloWorld;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private static void useTheTransducer() {
        List<String> dictionary = new ArrayList<String>();
        dictionary.add("GAMBOL");
        dictionary.add("MASINA");
        dictionary.add("STAFIDA");

        ITransducer<Candidate> transducer = HelloWorld.playWithTransducer(dictionary);
        HelloWorld.displayOutput(transducer);
    }

    public static void main(String[] args) {
        useTheTransducer();
    }

}