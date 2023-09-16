#include <iostream>

#include "antlr4-runtime.h"
#include "libs/RinhaLexer.h"
#include "libs/RinhaParser.h"
#include "RinhaToCppVisitor.h"

using namespace std;
using namespace antlr4;

int main(int argc, const char* argv[]) {
  std::ifstream stream;
  stream.open("print.rinha");

  ANTLRInputStream input(stream);
  RinhaLexer lexer(&input);
  CommonTokenStream tokens(&lexer);
  RinhaParser parser(&tokens);

  RinhaParser::CompilationUnitContext* tree = parser.compilationUnit();

  RinhaToCppVisitor visitor;
  visitor.visitCompilationUnit(tree);

  return 0;
}
