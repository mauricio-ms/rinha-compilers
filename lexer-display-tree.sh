#!/usr/bin/bash

# aliases doest't work on shell scripts

cd libs-java
java -Xmx500M -cp "/usr/local/lib/antlr-4.13.1-complete.jar:$CLASSPATH" org.antlr.v4.gui.TestRig Rinha compilationUnit -tree ../examples/test.rinha
