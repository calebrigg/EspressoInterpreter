public class Variable {
    private int value;
    private boolean initialized;
    private char title;
    public Variable(){
        initialized = false;
    }
    public void setValue(char c,int v){
        this.title = c;
        this.value = v;
        this.initialized = true;
    }
    public int getValue()/* throws UndefinedVariableException */ {
        if (!initialized) {
            return -1;
            /* throw new UndefinedVariableException();*/
        } else {
            return value;
        }
    }
        public int getTitle()/* throws UndefinedVariableException */{
            if (!initialized){
                return -1;
                /* throw new UndefinedVariableException();*/
            }
            else {
                return title;
            }

    }

}
