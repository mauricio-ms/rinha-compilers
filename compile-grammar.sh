#!/usr/bin/bash
rm libs/*
cd grammar
java -Xmx500M -cp "tools/antlr-4.13.1-complete.jar:$CLASSPATH" org.antlr.v4.Tool -Dlanguage=Cpp -visitor -o ../libs Rinha.g4
