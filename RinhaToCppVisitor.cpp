#include "RinhaToCppVisitor.h"

using namespace std;

std::any RinhaToCppVisitor::visitCompilationUnit(RinhaParser::CompilationUnitContext *ctx) {
  cout << "Hello, World!\n";
  return visitChildren(ctx);
}
