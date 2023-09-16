# minimum required CMAKE version
CMAKE_MINIMUM_REQUIRED(VERSION 3.7 FATAL_ERROR)

project("Rinha-Compiler")

list(APPEND CMAKE_MODULE_PATH ${CMAKE_CURRENT_SOURCE_DIR}/cmake)

# compiler must be 17
set(CMAKE_CXX_STANDARD 17)

# required if linking to static library
add_definitions(-DANTLR4CPP_STATIC)

# using /MD flag for antlr4_runtime (for Visual C++ compilers only)
set(ANTLR4_WITH_STATIC_CRT OFF)
# add external build for antlrcpp
include(ExternalAntlr4Cpp)
# add antrl4cpp artifacts to project environment
include_directories(${ANTLR4_INCLUDE_DIRS})

# set variable pointing to the antlr tool that supports C++
# this is not required if the jar file can be found under PATH environment
set(ANTLR_EXECUTABLE ${CMAKE_CURRENT_SOURCE_DIR}/tools/antlr-4.13.1-complete.jar)
# add macros to generate ANTLR Cpp code from grammar
find_package(ANTLR REQUIRED)

# Call macro to add lexer and grammar to your build dependencies.
antlr_target(RinhaGrammarParser grammar/Rinha.g4 VISITOR
								OUTPUT_DIRECTORY ${PROJECT_SOURCE_DIR}/libs/)

# include generated files in project environment
include_directories(${PROJECT_SOURCE_DIR} ${PROJECT_SOURCE_DIR}/libs/)

# add generated grammar to rinha binary target
add_executable(rinha main.cpp RinhaToCppVisitor.cpp
               ${ANTLR_RinhaGrammarParser_CXX_OUTPUTS})
target_link_libraries(rinha antlr4_static)

add_custom_command(TARGET rinha POST_BUILD
						  COMMAND ${CMAKE_COMMAND} -E copy_if_different
						  "${PROJECT_SOURCE_DIR}/examples/print.rinha"
						  $<TARGET_FILE_DIR:rinha>)
