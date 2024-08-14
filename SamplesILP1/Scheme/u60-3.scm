;;; $Id: u60-3.scm 405 2006-09-13 17:21:53Z queinnec $
(comment "le corps tourne")
(let ((x 1))
  (try-catch-finally
    (set! x 2)
    #f
    (print x) )
  (print x)
  (= x 2) )

;;; end of u60-3.scm
