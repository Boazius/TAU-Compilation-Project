
import java.io.*;
import java.io.PrintWriter;
import java_cup.runtime.Symbol;
import AST.*;
import CFG.*;
import IR.*;
import MIPS.*;

public class Main {
	static public void main(String argv[]) {
		Lexer l;
		Parser p;
		Symbol s;
		AST_PROGRAM AST;
		FileReader file_reader;
		PrintWriter file_writer;
		String inputFilename = argv[0];
		MIPSGenerator.mipsOutputFilename = argv[1];

		String dirname = "./output/";
		String filename = "SemanticStatus.txt";
		String semanticOutputFilename = dirname + filename;

		try {
			/********************************/
			/* [1] Initialize a file reader */
			/********************************/
			file_reader = new FileReader(inputFilename);

			/********************************/
			/* [2] Initialize a file writer */
			/********************************/
			file_writer = new PrintWriter(semanticOutputFilename);

			/******************************/
			/* [3] Initialize a new lexer */
			/******************************/
			l = new Lexer(file_reader);

			/*******************************/
			/* [4] Initialize a new parser */
			/*******************************/
			p = new Parser(l, semanticOutputFilename);

			/***********************************/
			/* [5] 3 ... 2 ... 1 ... Parse !!! */
			/***********************************/
			AST = (AST_PROGRAM) p.parse().value;

			/*************************/
			/* [6] Print the AST ... */
			/*************************/
			AST.PrintMe();

			/**************************/
			/* [7] Semant the AST ... */
			/**************************/
			AST.SemantMe();
			file_writer.print("OK");

			/**********************/
			/* [8] IR the AST ... */
			/**********************/
			AST.IRme();
			System.out.println("\n");

			/**********************************/
			/* [8.5] alocate temps for IR ... */
			/**********************************/
			CFG g=new CFG();
			g.liveness();
			g.K_color();
			/***********************/
			/* [9] MIPS the IR ... */
			/***********************/
			IR.getInstance().MIPSme();

			/**************************************/
			/* [10] Finalize AST GRAPHIZ DOT file */
			/**************************************/
			AST_GRAPHVIZ.getInstance().finalizeFile();

			/***************************/
			/* [11] Finalize MIPS file */
			/***************************/

			MIPSGenerator.getInstance().finalizeFile();

			/**************************/
			/* [12] Close output file */
			/**************************/
			file_writer.close();
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
