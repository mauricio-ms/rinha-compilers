import java.io.FileInputStream;
import java.io.IOException;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import antlr.RinhaLexer;
import antlr.RinhaParser;

public class Main {
	
	public static void main(String[] args) {
        System.out.println("== START");
		try {
            var input = new FileInputStream("examples/test.rinha");
            var chars = CharStreams.fromStream(input);
            var lexer = new RinhaLexer(chars);
            var tokens = new CommonTokenStream(lexer);
            var parser = new RinhaParser(tokens);
            parser.setBuildParseTree(true);
            var tree = parser.compilationUnit().getRuleContext();
            var visitor = new RinhaToJavaVisitor();
            visitor.visit(tree);
            //ParseTreeWalker.DEFAULT.walk(listener, tree);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("== END");
	}
}
