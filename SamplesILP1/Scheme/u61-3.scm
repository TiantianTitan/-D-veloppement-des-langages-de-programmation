;;; $Id: u61-3.scm 405 2006-09-13 17:21:53Z queinnec $
(comment "le finally tourne")
(let ((x 1))
  (try-catch-finally
    x
    #f
    (set! x 3) )
  (print x)
  (= x 3) )

;;; end of u61-3.scm
