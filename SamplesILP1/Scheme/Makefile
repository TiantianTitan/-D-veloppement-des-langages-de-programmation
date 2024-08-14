# ******************************************************************
# ILP -- Implementing a Programming Language
# GPL version>=2
# ******************************************************************

work : generate.xml

# Convert programs written in Scheme towards an XML syntax.
# Also generate the result and what is printed.

SCHEME	=	bigloo -i

generate.xml : scm2xml.scm
	-cd .. ; rm -f *.xml *.result *.print *.c
	{ echo "(load \"scm2xml.scm\")" ; \
	  for s in u*-[0-9].scm u599[1-5]*gfv.scm ; do \
	     echo "(quote \"Convertir $$s en $${s%.scm}.xml\")" ; \
	     echo "(handle-file \"$$s\" \"../$${s%.scm}\")" ; \
	  done ; } | ${SCHEME}

JAVA	=	java
JING	=	../../Java/jars/jing.jar

# Focus on one precise test:

BASE	=	u57-2

test.by.hand :
	{ echo '(load "scm2xml.scm")' ; \
	  echo '(handle-file "${BASE}.scm" "../${BASE}")' ; } | \
		${SCHEME}
	${JAVA} -jar ${JING} ../grammar9.rng ../${FILE}.xml
	cd ../ && cat ${BASE}.xml ${BASE}.result ${BASE}.print
# (handle-file takes two arguments: the file to handle and the
# file where to store the result.

# end of Makefile
