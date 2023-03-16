package AST;

public class AST_Node_Serial_Number {
    private static AST_Node_Serial_Number instance = null;
    public int SerialNumber;

    protected AST_Node_Serial_Number() {
    }

    private static AST_Node_Serial_Number getInstance() {
        if (instance == null) {
            instance = new AST_Node_Serial_Number();
            instance.SerialNumber = 0;

        }
        return instance;
    }

    /**********************************/
    /* GET A UNIQUE SERIAL NUMBER ... */

    /**********************************/
    public static int getFresh() {
        return AST_Node_Serial_Number.getInstance().get();
    }

    /**********************************/
    /* GET A UNIQUE SERIAL NUMBER ... */

    /**********************************/
    public int get() {
        return SerialNumber++;
    }
}
