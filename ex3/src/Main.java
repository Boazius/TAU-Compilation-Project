import AST.AST_GRAPHVIZ;
import AST.AST_PROGRAM;
import UtilFunctions.HelperFunctions;

import java.io.FileReader;
import java.io.PrintWriter;

public class Main {
    static public void main(String argv[]) {
        Lexer l;
        Parser p;
		AST_PROGRAM AST;
        FileReader file_reader;
        PrintWriter file_writer;
        String inputFilename = argv[0];
        String outputFilename = argv[1];

        try {
            /********************************/
            /* [1] Initialize a file reader */
            /********************************/
            file_reader = new FileReader(inputFilename);

            /********************************/
            /* [2] Initialize a file writer */
            /********************************/
            file_writer = new PrintWriter(outputFilename);
            HelperFunctions.setFileWriter(file_writer);

            /******************************/
            /* [3] Initialize a new lexer */
            /******************************/
            l = new Lexer(file_reader);

            /*******************************/
            /* [4] Initialize a new parser */
            /*******************************/
            p = new Parser(l, file_writer);

            /***********************************/
            /* [5] 3 ... 2 ... 1 ... Parse !!! */
            /***********************************/
            AST = (AST_PROGRAM) p.parse().value;

            /*************************/
            /* [6] Print the AST ... */
            /*************************/
            AST.PrintMe();

            /*************************************/
            /* [9] Finalize AST GRAPHIZ DOT file */
            /*************************************/
            AST_GRAPHVIZ.getInstance().finalizeFile();

            /**************************/
            /* [7] Semant the AST ... */
            /**************************/
            /*process the AST to remove useless nodes and things*/
            HelperFunctions.reduceDecs(AST);
            HelperFunctions.organizeClassDecsInAst(AST);
            /*TODO add global functions print thingy*/
            /*semantic analysis*/
            AST.SemantMe();
            file_writer.write("OK\n");

            /*************************/
            /* [8] Close output file */
            /*************************/
            file_writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


