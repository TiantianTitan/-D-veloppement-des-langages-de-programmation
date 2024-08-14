#!/bin/bash

# Ce script compile la version de BohemGC intégrée dans le projet
# (7.2f).
# Ceci n'est nécessaire que si BohemGC n'est pas déjà installé
# sur votre système.

if test -f /usr/include/gc.h; then
   echo "BoehmGC est déja installé sur votre système, compilation inutile"
   exit 1
fi

SRC=gc-7.2f.tgz
DST=gc-7.2

# Répertoire contenant le script
DIR=`dirname $0`
# DIR=`pwd -P $DIR`

if test ! -f $DIR/$SRC; then
    echo "Les sources $SRC ne sont pas trouvées dans $DIR" 
    exit 1
fi

cd $DIR && \
    rm -rf $DST && \
    tar xzf $SRC && \
    cd $DST && \
    ./configure && \
    make -j && \
    make check
