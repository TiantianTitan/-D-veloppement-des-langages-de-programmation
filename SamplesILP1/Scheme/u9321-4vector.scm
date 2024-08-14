;;; $Id$
(comment "longueur d'un non-vecteur")
(expected-result "OK")
(expected-printing "")
(let ((v 9321))
  (try-catch-finally
   (begin 
     (vector-length v)
     (set! v "KO") )
   (lambda (e)
     (set! v "OK") )
   #f )
  v )

;;; end of u9321-4vector.scm
