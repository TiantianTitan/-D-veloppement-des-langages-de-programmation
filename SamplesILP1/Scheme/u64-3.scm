;;; $Id: u64-3.scm 405 2006-09-13 17:21:53Z queinnec $
(comment "Le catcher recupere bien l'exception")
(let ((x 1))
  (try-catch-finally
    (begin 
      (throw 6)
      (set! x 4) )
    (lambda (exc)
      (set! x exc) )
    #f )
  (print x)
  (= x 6) )

;;; end of u64-3.scm
