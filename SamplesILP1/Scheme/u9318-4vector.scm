;;; $Id$
(comment "ecriture hors-apres vecteur")
(expected-result "OK")
(expected-printing "")
(let ((state "debut"))
  (try-catch-finally
   (let ((v (make-vector 3 "a")))
     (vector-set! v 11 "qqch")
     (set! state "KO") )
   (lambda (e)
     (set! state "OK") )
   #f )
  state )

;;; end of u9318-4vector.scm
