;;; $Id$
(comment "lambda closing one variable, applied out of scope")
((let ((x 4))
   (let ((f (lambda (y) (+ x y))))
     f ) )
 7500 )

;;; end of u7504-5.scm
