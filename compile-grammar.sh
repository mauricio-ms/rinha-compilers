#!/usr/bin/bash
rm src/main/java/antlr/*
cd grammar
java -Xmx500M -cp "tools/antlr-4.13.1-complete.jar:$CLASSPATH" org.antlr.v4.Tool -visitor -o ../src/main/java/antlr Rinha.g4
