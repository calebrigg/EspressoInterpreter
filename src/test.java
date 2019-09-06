import java.io.*;
import java.util.*;
public class test {
    public static void main(String[] args) throws FileNotFoundException {

        File fr = new File(args[0]);
        Interpreter interpreter = new Interpreter(fr);

    }
}