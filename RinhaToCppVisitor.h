#include "libs/RinhaBaseVisitor.h"
#include "libs/RinhaParser.h"

class RinhaToCppVisitor : RinhaBaseVisitor {
 public:
  std::any visitCompilationUnit(RinhaParser::CompilationUnitContext *ctx);

  std::any visitPrint(RinhaParser::PrintContext *ctx);
};