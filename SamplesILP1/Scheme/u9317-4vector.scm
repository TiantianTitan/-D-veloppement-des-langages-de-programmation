;;; $Id$
(comment "ecriture hors-avant vecteur")
(expected-result "OK")
(expected-printing "")
(let ((state "debut"))
  (try-catch-finally
   (let ((v (make-vector 3 "a")))
     (vector-set! v -1 "qqch")
     (set! state "KO") )
   (lambda (e)
     (set! state "OK") )
   #f )
  state )

;;; end of u9317-4vector.scm
