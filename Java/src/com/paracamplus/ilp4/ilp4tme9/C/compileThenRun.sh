#! /bin/bash

# Utilisation :
#
# ./compileThenRun.sh [options] <fichiers .c>
#
# Ce programme compile les fichiers .c donnés en ligne de commande,
# les lie avec la bibliothèque d'éxecution ILP, et exécute le programme
# généré.
# Il est utilisé comme ultime étape du compilateur ILP, pour compiler en
# binaire le fichier C issu de la compilation ILP et pour le lancer.
#
# Tous les fichiers .c du répertoire contenant le script (C/) par défaut
# sont considérés comme faisant partie de la bibliothèque d'exécution
# ILP et sont inclus dans la compilation.
#
# Options reconnues:
#   +v         affiche la ligne de commande de compilation
#   +gc        active BoehmGC (s'il est trouvé)
#   +gdb       lance le programme avec gdb
#   +valgrind  lance le programme avec valgrind



# Traitement des options en ligne de commande
OPTS=""
TOOL=""
while : ; do
    case "$1" in
        "")
            # fin
            break;;
        +v)
            VERBOSE=1
            shift;;
        +gc)
            GC=1
            shift;;
        +gdb)
            TOOL="gdb"
            shift;;
        +valgrind)
            TOOL="valgrind"
            shift;;
        -l* | -D*)
            OPTS="$OPTS $1"
            shift;;
        *.c)
            SRC="$1"
            shift;;
        *)
            echo "Option $1 inconnue"
            exit 2;;
    esac
done



# Options de compilation C:
# - afficher tous les "warning", sauf les variables inutilisées
# - compilation en mode débogage pour faciliter l'utilistation d'un débogueur
CFLAGS="-Wall -Wno-unused-variable -Wno-unused-but-set-variable -Wno-unused-label -std=c99 -pedantic -g"

# Répertoire contenant le script
DIR=`dirname $0`
# DIR=`pwd -P $DIR`

# Fichiers C contenant la bibliothèque d'exécution
# Par défaut, tous les fichiers .c du répertoire contenant le script
C_RUNTIME="$DIR/*.c"

# Bibliothèques à utiliser
LIBS="-lm"

# Directives -D à utiliser
DEFS=""

# Nom de l'exécutable produit
TARGET=`mktemp "/tmp/dlp-XXXXXXXXXX"`

# Activation du GC, si demandé et trouvé
if test "x$GC" != "x"; then
    # Détection de BoehmGC
    if test -f $DIR/gc-8.2.2/include/gc.h -a -f $DIR/gc-8.2.2/.libs/libgc.so; then
        # BoehmGC compilé dans le répertoire courant
        DEFS="-DWITH_GC $DEFS -I$DIR/gc-8.2.2/include/"
        LIBS="-L$DIR/gc-8.2.2/.libs -lgc"
    elif test -f /usr/include/gc.h; then
        # BoehmGC est installé sur le système
        DEFS="-DWITH_GC $DEFS"
        LIBS="-lgc $LIBS"
    else
        # BoehmGC non trouvé
        GC=""
    fi
fi

# Compilation et exécution
if test "x$VERBOSE" != "x"; then
    echo gcc ${CFLAGS} -o "$TARGET" -I. -I$DIR $DEFS $C_RUNTIME "$SRC" $OPTS $LIBS
    echo $TOOL "$TARGET"
fi
gcc ${CFLAGS} -o "$TARGET" -I. -I$DIR $DEFS $C_RUNTIME "$SRC" $OPTS $LIBS && $TOOL "$TARGET"
