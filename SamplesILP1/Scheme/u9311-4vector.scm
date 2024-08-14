;;; $Id$
(comment "allocation vecteur avec taille negative")
(expected-result "OK")
(expected-printing "")
(let ((state "debut"))
  (try-catch-finally
   (begin 
     (make-vector -2 "a")
     (set! state "KO") )
   (lambda (e)
     (set! state "OK") )
   #f )
  state )
  
;;; end of u9311-4vector.scm
