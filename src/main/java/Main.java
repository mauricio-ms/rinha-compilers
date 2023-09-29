import java.io.FileInputStream;
import java.io.IOException;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import antlr.RinhaLexer;
import antlr.RinhaParser;

public class Main {
	
	public static void main(String[] args) {
		try {
            var input = new FileInputStream("/var/rinha/source.rinha");
            var chars = CharStreams.fromStream(input);
            var lexer = new RinhaLexer(chars);
            var tokens = new CommonTokenStream(lexer);
            var parser = new RinhaParser(tokens);
            parser.setBuildParseTree(true);
            var tree = parser.compilationUnit().getRuleContext();
            var visitor = new RinhaInterpreter();
            visitor.visit(tree);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
	}
}
