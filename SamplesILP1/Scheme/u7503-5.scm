;;; $Id$
(comment "lambda closing one variable, applied out of scope, using one local variable")
(let ((x 3))
  ((let ((f (lambda (y) (+ x y))))
     f )
   7500 ) )

;;; end of u7503-5.scm
