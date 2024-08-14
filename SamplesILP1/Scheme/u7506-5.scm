;;; $Id$
((let ((x 500))
   ((let ((mkf (lambda (y z)
                 (lambda (t)
                   (+ x (+ y (+ z t))) ) )))
      mkf )
    7000 5 ) )
 1 )

;;; end of u7506-5.scm
