
import java.io.*;
import java.io.PrintWriter;

import java_cup.runtime.Symbol;

public class Main {
    static public void main(String[] argv) {
        /*define new lexer*/
        Lexer currLexer;
        Symbol currSymbol;
        FileReader file_reader;
        PrintWriter file_writer;
        String inputFilename = argv[0];
        String outputFilename = argv[1];

        try {
            /* [1] Initialize a file reader */
            file_reader = new FileReader(inputFilename);

            /* [2] Initialize a file writer */
            file_writer = new PrintWriter(outputFilename);

            /* [3] Initialize a new lexer */
            currLexer = new Lexer(file_reader);

            /* [4] Read next token */
            currSymbol = currLexer.next_token();

            /* [5] Main reading tokens loop */
            while (currSymbol.sym != TokenNames.EOF) {
                /*skip comments */
                if (currSymbol.sym == TokenNames.COMMENT) {
                    currSymbol = currLexer.next_token();
                    continue;
                }
                /*if the symbol is error, just output error*/
                if (currSymbol.sym == TokenNames.error) {
                    file_writer.close();
                    file_writer = new PrintWriter(outputFilename);
                    System.out.println("ERROR");
                    file_writer.print("ERROR");
                    break;
                }

                /* [6] Print to console -
                 * construct a string like LPAREN[7,8] for line 7 char 8
                 * or INT(74)[3,8] or STRING("Dan")[2,5] or ID(numPts)[1,6] */

                String tokenString = "";
                tokenString += TokenNames.numToName(currSymbol.sym);
                /*something in the braces, like INT(123), STRING("sup", ID(hey)*/
                if (currSymbol.value != null) {
                    tokenString += ("(" + currSymbol.value + ")");
                }
                tokenString += "[";
                tokenString += currLexer.getLine();
                tokenString += ",";
                tokenString += currLexer.getTokenStartPosition();
                tokenString += "]\n";
                System.out.print(tokenString);

                /* [7] Print to file */
                file_writer.print(tokenString);

                /* [8] Read next token */
                currSymbol = currLexer.next_token();
            }

            /* [9] Close lexer input file */
            currLexer.yyclose();

            /* [10] Close output file */
            file_writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


