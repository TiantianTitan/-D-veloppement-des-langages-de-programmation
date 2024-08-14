#!/bin/bash

#
# Compiles toutes les grammaires XML (.rng)
#

XML_GRAMMARS="XMLGrammars/*.rng `find Java/src -name \*.rng`"

JAVA=java
TRANG=Java/jars/trang.jar

for i in $XML_GRAMMARS; do
    echo "Compiling XML grammar $i"
    o=`echo $i | sed s/rng/rnc/g`
    $JAVA -jar $TRANG -i encoding=utf-8 -o encoding=utf-8 $i $o
done
