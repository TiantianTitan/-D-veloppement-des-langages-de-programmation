;;; $Id$
(comment "lecture hors-avant vecteur")
(expected-result "OK")
(expected-printing "")
(let ((state "debut"))
  (try-catch-finally
   (let ((v (make-vector 3 "a")))
     (vector-get v -1)
     (set! state "KO") )
   (lambda (e)
     (set! state "OK") )
   #f )
  state )

;;; end of u9313-4vector.scm
