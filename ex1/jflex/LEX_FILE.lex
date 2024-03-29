/***************************/
/* FILE NAME: LEX_FILE.lex */
/***************************/

/*************/
/* USER CODE */
/*************/
import java_cup.runtime.*;

/******************************/
/* DOLAR DOLAR - DON'T TOUCH! */
/******************************/

%%

/************************************/
/* OPTIONS AND DECLARATIONS SECTION */
/************************************/

/*****************************************************/
/* Lexer is the name of the class JFlex will create. */
/* The code will be written to the file Lexer.java.  */
/*****************************************************/
%class Lexer

/********************************************************************/
/* The current line number can be accessed with the variable yyline */
/* and the current column number with the variable yycolumn.        */
/********************************************************************/
%line
%column

/*******************************************************************************/
/* Note that this has to be the EXACT same name of the class the CUP generates */
/*******************************************************************************/
%cupsym TokenNames

/******************************************************************/
/* CUP compatibility mode interfaces with a CUP generated parser. */
/******************************************************************/
%cup

/****************/
/* DECLARATIONS */
/****************/
/*****************************************************************************/
/* Code between %{ and %}, both of which must be at the beginning of a line, */
/* will be copied verbatim (letter to letter) into the Lexer class code.     */
/* Here you declare member variables and functions that are used inside the  */
/* scanner actions.                                                          */
/*****************************************************************************/
%{
	/*********************************************************************************/
	/* Create a new java_cup.runtime.Symbol with information about the current token */
	/*********************************************************************************/
	private Symbol symbol(int type)               {return new Symbol(type, yyline, yycolumn);}
	private Symbol symbol(int type, Object value) {return new Symbol(type, yyline, yycolumn, value);}

	/*******************************************/
	/* Enable line number extraction from main */
	/*******************************************/
	public int getLine() { return yyline + 1; }
	/*function in the lexer skeleton, im adding just in case */
	public int getCharPos() { return yycolumn;   }

	/**********************************************/
	/* Enable token position extraction from main */
	/**********************************************/

	public int getTokenStartPosition() { return yycolumn + 1; }

	/* check string is a number between 0 and 2^15 -1. */
	/* returns -1 if invalid, else returns the number in int form */
	public int getValidInt(String numString)
	{
	    /*check valid length of number */
	    if (numString.length() > 5)
	        return -1;
	    /*check if leading zeroes */
	    if(numString.charAt(0)=='0')
	    {
	        if(numString.length()>1)
	            return -1;
	        return 0;
	    }
	    /*parse number */
	    int num = new Integer(numString);
	    if (num >= 0 && num <= 32767)
	        return num;
	    return -1;
	}
%}

/***********************/
/* MACRO DECALARATIONS */
/***********************/
LineTerminator	= \r | \n | \r\n
WhiteSpace		= {LineTerminator} | [ \t] | [ \t\f]
INTEGER			= [0-9]+
LETTER = [a-z] | [A-Z]
ALPHANUM = {LETTER} | [0-9]
ID = {LETTER}+{ALPHANUM}*
STRING = [\"]{LETTER}*[\"]
InputCarriage = [^\r\n]

Brace = "(" | ")" | "[" | "]" | "{" | "}"
Punctuation = "?" | "!" | "." | ";"
Addition = "+" | "-"
AllowedCommentCharNoStar = {WhiteSpace} | {ALPHANUM} | {Brace} | {Punctuation} | {Addition} | "/"
AllowedCommentCharNoSlash = {WhiteSpace} | {ALPHANUM} | {Brace} | {Punctuation} | {Addition} | "*"
AllowedCommentChars = {WhiteSpace} | {ALPHANUM} | {Brace} | {Punctuation} | {Addition} | "*" | "/"
AllowedCommentCharNoLineBrakes = {ALPHANUM} | {Brace} | {Punctuation} | {Addition} | "/" | "*" | [\t] | [ \t\f]

DoubleSlashComment = "//"{AllowedCommentCharNoLineBrakes}* {LineTerminator}
AnsiComment = "/*" ( {AllowedCommentCharNoStar} | "*"{AllowedCommentCharNoSlash} )*  (("*")*)"*/"


/******************************/
/* DOLAR DOLAR - DON'T TOUCH! */
/******************************/

%%

/************************************************************/
/* LEXER matches regular expressions to actions (Java code) */
/************************************************************/

/**************************************************************/
/* YYINITIAL is the state at which the lexer begins scanning. */
/* So these regular expressions will only be matched if the   */
/* scanner is in the start state YYINITIAL.                   */
/**************************************************************/

<YYINITIAL> {

":=" 					{ return symbol(TokenNames.ASSIGN);}
"=" 					{ return symbol(TokenNames.EQ);}
"["  					{ return symbol(TokenNames.LBRACK);}
"<" 					{ return symbol(TokenNames.LT);}
"]"  					{ return symbol(TokenNames.RBRACK );}
">" 					{ return symbol(TokenNames.GT);}
"{" 					{ return symbol(TokenNames.LBRACE);}
"}" 					{ return symbol(TokenNames.RBRACE);}
"+"  					{ return symbol(TokenNames.PLUS);}
"-"  					{ return symbol(TokenNames.MINUS);}
"*"  					{ return symbol(TokenNames.TIMES);}
"/" 					{ return symbol(TokenNames.DIVIDE);}
"," 					{ return symbol(TokenNames.COMMA);}
"."  					{ return symbol(TokenNames.DOT);}
";" 					{ return symbol(TokenNames.SEMICOLON);}
"("                     { return symbol(TokenNames.LPAREN);}
")"                     { return symbol(TokenNames.RPAREN);}
"array"					{ return symbol(TokenNames.ARRAY);}
"class"					{ return symbol(TokenNames.CLASS);}
"extends"				{ return symbol(TokenNames.EXTENDS);}
"nil"					{ return symbol(TokenNames.NIL);}
"while"					{ return symbol(TokenNames.WHILE);}
"return"				{ return symbol(TokenNames.RETURN);}
"if"					{ return symbol(TokenNames.IF);}
"new"					{ return symbol(TokenNames.NEW);}
{DoubleSlashComment}    { return symbol(TokenNames.COMMENT);}
{AnsiComment}      		{ return symbol(TokenNames.COMMENT);}
"int"					{ return symbol(TokenNames.TYPE_INT);}
"string"				{ return symbol(TokenNames.TYPE_STRING);}
"void"					{ return symbol(TokenNames.TYPE_VOID);}

{INTEGER}				{
         int num = getValidInt(yytext());
         if(num==-1)
         {
            return symbol(TokenNames.error);
         } else {
            return symbol(TokenNames.INT, num);
         }
}

{ID}				{ return symbol(TokenNames.ID, new String( yytext()));}
{WhiteSpace}		{ /* nothing */ }

{STRING}			{return symbol(TokenNames.STRING, new String( yytext()));}

<<EOF>>				{ return symbol(TokenNames.EOF);}

"/*"                { return symbol(TokenNames.error);}
"//"                { return symbol(TokenNames.error);}
.                   { return symbol(TokenNames.error);}
}
