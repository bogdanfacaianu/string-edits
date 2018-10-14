import com.string.edits.HelloWorld;

public class Main {

    public static void main(String[] args) {
        System.out.println(HelloWorld.getHello("Gigel!"));
        String source = "GUMBO";
        String target = "GAMBOL";
        System.out.print("The distance between *" + source + "* and *" + target + "* is: "
                + HelloWorld.getDistanceBetween("GUMBO", "GAMBOL"));
    }

}