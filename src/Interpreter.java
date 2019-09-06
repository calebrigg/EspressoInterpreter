import javax.sound.sampled.Line;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Interpreter {
    ArrayList<Variable> vars = new ArrayList<Variable>();
    Variable tempvar = new Variable(); //it adds the under the same tempvar name, creating an issue. If i rewrite a lot of code, var name can be identifier.

    public Interpreter(File file) throws FileNotFoundException {
        Scanner sc = new Scanner(file);
        LineEvaluator lineEvaluator = new LineEvaluator(this.vars);

        while (sc.hasNext()) {
            lineEvaluator.interpret(sc.nextLine());
        }

        sc.close();
    }
}
