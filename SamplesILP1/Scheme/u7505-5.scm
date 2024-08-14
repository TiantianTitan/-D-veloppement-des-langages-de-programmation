;;; $Id$
(comment "Binary lambda closing one variable, applied out of scope")
((let ((x 500))
   (let ((f (lambda (y z) (+ x (+ y z)))))
     f ) )
 7000 5 )

;;; end of u7505-5.scm
