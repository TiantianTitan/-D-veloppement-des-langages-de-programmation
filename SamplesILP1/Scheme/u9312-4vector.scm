;;; $Id$
(comment "allocation vecteur avec taille non numerique")
(expected-result "OK")
(expected-printing "")
(let ((state "debut"))
  (try-catch-finally
   (begin 
     (make-vector #t "a")
     (set! state "KO") )
   (lambda (e)
     (set! state "OK") )
   #f )
  state )

;;; end of u9312-4vector.scm
