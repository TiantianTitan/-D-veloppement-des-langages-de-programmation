;;; $Id: u63-3.scm 405 2006-09-13 17:21:53Z queinnec $
(comment "signaler l'exception fait bien sortir du corps")
(let ((x 1))
  (try-catch-finally
    (begin 
      (throw 11)
      (set! x 4) )
    #f
    (set! x (+ 4 x)) )
  (print x)
  (= x 5) )

;;; end of u63-3.scm
