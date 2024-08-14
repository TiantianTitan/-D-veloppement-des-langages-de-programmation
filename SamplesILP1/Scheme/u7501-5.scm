;;; $Id$
(comment "lambda closing one variable but immediately applied")
(let ((x 1))
  ((lambda (y) (+ x y))
   7500 ) )

;;; end of u7501-5.scm
