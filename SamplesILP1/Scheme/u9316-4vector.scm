;;; $Id$
(comment "acces non numerique en ecriture vecteur")
(expected-result "OK")
(expected-printing "")
(let ((state "debut"))
  (try-catch-finally
   (let ((v (make-vector 3 "a")))
     (vector-set! v "b" 12345)
     (set! state "KO") )
   (lambda (e)
     (set! state "OK") )
   #f )
  state )

;;; end of u9316-4vector.scm
