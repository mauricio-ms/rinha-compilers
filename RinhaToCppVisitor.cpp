#include "RinhaToCppVisitor.h"

using namespace std;

std::any RinhaToCppVisitor::visitCompilationUnit(RinhaParser::CompilationUnitContext *ctx) {
  return visitChildren(ctx);
}

std::any RinhaToCppVisitor::visitPrint(RinhaParser::PrintContext *ctx) {
  cout << ctx->term()->STRING()->getText();
  return visitChildren(ctx);
}
