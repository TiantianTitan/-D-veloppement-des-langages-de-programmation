#!/bin/bash

#
# Compile toutes les grammaires ANTLR4
#

ANTLR_GRAMMARS="ANTLRGrammars/*.g4 `find Java/src -name \*.g4`"

JAVA=java
ANTLR=`pwd`/Java/jars/antlr-4.4-complete.jar
ANTLR_DST=`pwd`/target/generated-sources/antlr4/antlr4

mkdir -p $ANTLR_DST

for i in $ANTLR_GRAMMARS; do
    echo "Compiling ANTLR4 grammar $i"
    (cd `dirname $i`; $JAVA -jar $ANTLR -o $ANTLR_DST `basename $i`)
done
