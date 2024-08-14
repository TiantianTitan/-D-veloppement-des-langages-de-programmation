;;; $Id: u51-2.scm 405 2006-09-13 17:21:53Z queinnec $
(comment "double affectation")
(let ((x 49))
  (print x)
  (set! x (+ x 1))
  (print x)
  (set! x (+ x 1))
  (print x)
  x )

;;; end of u51-2.scm
