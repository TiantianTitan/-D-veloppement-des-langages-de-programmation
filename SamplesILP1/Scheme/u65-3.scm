;;; $Id: u65-3.scm 405 2006-09-13 17:21:53Z queinnec $
(comment "Le catcher recupere bien l'exception et le finally tourne")
(let ((x 1))
  (try-catch-finally
    (begin 
      (throw 6)
      (set! x 4) )
    (lambda (exc)
      (set! x exc) )
    (set! x (+ 1 x)) )
  (print x)
  (= x 7) )

;;; end of u65-3.scm
