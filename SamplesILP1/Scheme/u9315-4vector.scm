;;; $Id$
(comment "lecture vecteur avec index non numerique")
(expected-result "OK")
(expected-printing "")
(let ((state "debut"))
  (try-catch-finally
   (let ((v (make-vector 3 "a")))
     (vector-get v "b")
     (set! state "KO") )
   (lambda (e)
     (set! state "OK") )
   #f )
  state )

;;; end of u9315-4vector.scm
