;;; $Id: u645-3.scm 405 2006-09-13 17:21:53Z queinnec $
(comment "Le catcher recupere bien une exception interne")
(expected-result #t)
(expected-printing 6)
(let ((x 1))
  (try-catch-finally
    (begin 
      (+ 3 "foo")
      (set! x 4) )
    (lambda (exc)
      (set! x (+ 5 x)) )
    #f )
  (print x)
  (= x 6) )

;;; end of u645-3.scm
