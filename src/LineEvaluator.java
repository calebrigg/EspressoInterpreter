import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class LineEvaluator {

    private int lnNum = 0;
    private String line="";
    private ArrayList<Variable> vars;

    public LineEvaluator(ArrayList<Variable> vars) {
        this.vars = vars;
    }

    static int Precedence(char ch){
        switch (ch){
            case '+':
            case '-':
                return 1;

            case '*':
            case '/':
            case '%':
                return 2;

            case '^':
                return 3;
        }
        return -1;
    }


    Variable varExists(char c){
        Variable tempvar;
        for (int i = 0; i<vars.size(); i++){
            tempvar = vars.get(i);
            if (tempvar.getTitle()==c){
                return tempvar;
            }
        }
        return null;
    }

    int getIndex(char c) {
        Variable tempvar;
        for (int i = 0; i <= vars.size(); i++) {
            tempvar = vars.get(i);
            if (tempvar.getTitle() == c) {
                return i;
            }
        }
        return -1;
    }

    public void interpret(String line) {
        lnNum++;
        this.line = line;

        String thing, expression;
        try {
            if (line.startsWith("print")) {
                String real = "";
                int i = line.indexOf('t');

                thing = line.substring(0, i);
        //        if (thing != "print") {
        //        }//error
                expression = line.substring(i + 2, line.length());
                real = this.infixConverter(expression);
                System.out.println(real);
            } else if (line.startsWith("read")) {
                Scanner in = new Scanner(System.in);
                int i = line.indexOf('d');
                thing = line.substring(0, i + 1);
                expression = line.substring(i + 2, line.length());
                char c2 = expression.charAt(0);
                if (expression.length() > 1) {
                }//error
                System.out.println("Enter a value for " + expression + ": ");
                String vv = in.nextLine();
                vv = this.infixConverter(vv);
                int vvv = Integer.parseInt(vv);
                Variable tempvar = new Variable();
                tempvar.setValue(c2, vvv);
                vars.add(tempvar);
            } else if (line.contains("=")) {
                int i = line.indexOf('=');
                int tempval;
                thing = line.substring(0, i - 1);
                expression = line.substring(i + 2, line.length());
                if (thing.length() > 1) {
                    System.out.println("Line "+lnNum+". "+line);
                    System.out.println("Syntax Error");
                    System.exit(0);
                }
                expression = this.infixConverter(expression);
                char c = thing.charAt(0);

                Variable tempvar = this.varExists(c);
                if (tempvar != null) {
                    tempval = Integer.parseInt(expression);
                    tempvar.setValue(c, tempval);
                    int f = vars.indexOf(thing);
                    vars.set(f, tempvar);
                } else if (Character.isLetter(c)) {
                    tempval = Integer.parseInt(expression);
                    tempvar = new Variable();
                    tempvar.setValue(c, tempval);

                    vars.add(tempvar);
                } else {
                    System.out.println("Line "+lnNum+". "+line);
                    System.out.println("Syntax Error");
                    System.exit(0);
                }
            } else {

                System.out.println("Line "+lnNum+". "+line);
                System.out.println("Syntax Error");
                System.exit(0);
            }
        }catch(Exception e){
            System.out.println("Line "+lnNum+". "+line);
            System.out.println("Syntax Error");
            System.exit(0);

        }
    }



        String infixConverter(String infix) throws Exception {

            Variable tvar = new Variable();

            String pfix = new String("");
            Stack<Character> stack = new Stack<>();

            for (int i=0; i < infix.length(); ++i) {
                char curr = infix.charAt(i);
                if (Character.isDigit(curr)||curr==' ') {
                    pfix += curr;
                }
                else if (Character.isLetter(curr)){
                    //  Read reader = new Read();
                    if (this.varExists(curr)!=null){
                        //  Variable tvar = new Variable();
                        int n = this.getIndex(curr);
                        tvar = this.vars.get(n);
                        pfix += tvar.getValue();
                    }
                    else{
                        System.out.println("Line "+lnNum+". "+line);
                        System.out.println("Error: variable " + curr + " is not defined.");
                        System.exit(0);
                    }
                }

                else if (curr == '('){
                    stack.push(curr);
                }
                else if (curr == ')'){
                    while (!stack.isEmpty() && stack.peek() != '(') {
                        pfix += stack.pop();
                    }

                    if (!stack.isEmpty() && stack.peek() != '(') {
                        return "Invalid Expression"; // invalid expression
                    }
                    else {
                        stack.pop();
                    }
                }
                else {
                    while (!stack.isEmpty() && Precedence(curr) <= Precedence(stack.peek())) {
                        pfix += stack.pop();
                    }
                    stack.push(curr);
                }
            }
            while (!stack.isEmpty()) {
                pfix += stack.pop();
            }
            int sol = postfixEvaluation(pfix);
            pfix = Integer.toString(sol);
            return pfix;

        }


        Integer postfixEvaluation(String pfix) throws Exception {
            Stack<Integer> stack = new Stack<>();

            for(int i = 0; i < pfix.length(); i++){
                char curr = pfix.charAt(i);

                if(curr == ' '){
                    continue;
                }

                else if(Character.isDigit(curr)){
                    int num = 0;

                    while(Character.isDigit(curr)){
                        num = num*10 + (int)(curr-'0');
                        i++;
                        if (i==pfix.length()){break;}
                        curr = pfix.charAt(i);
                    }
                    i--;

                    stack.push(num);
                }

                else{
                    int val1 = stack.pop();
                    int val2 = stack.pop();

                    switch(curr){
                        case '+':
                            stack.push(val2+val1);
                            break;

                        case '-':
                            stack.push(val2-val1);
                            break;

                        case '/':
                            stack.push(val2/val1);
                            break;

                        case '%':
                            stack.push(val2%val1);
                            break;

                        case '*':
                            stack.push(val2*val1);
                            break;
                    }
                }
            }
            if(stack.size()!=1)
                throw new Exception();
            return stack.pop();
        }
}
