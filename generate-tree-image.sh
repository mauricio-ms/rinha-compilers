#!/usr/bin/bash
rm -f libs/*
cd grammar
java -Xmx500M -cp "/tools/antlr-4.13.1-complete.jar:$CLASSPATH" org.antlr.v4.Tool -visitor -o ../libs Rinha.g4
cd ../libs
javac Rinha*.java
java -Xmx500M -cp "/tools/antlr-4.13.1-complete.jar:$CLASSPATH" org.antlr.v4.gui.TestRig Rinha compilationUnit -gui ../examples/fib.rinha
