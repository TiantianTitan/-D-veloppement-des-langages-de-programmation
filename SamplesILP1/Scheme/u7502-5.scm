;;; $Id$
(comment "lambda closing one variable and applied out of scope")
((let ((x 2))
   (lambda (y) (+ x y)) )
 7500 )

;;; end of u7502-5.scm
