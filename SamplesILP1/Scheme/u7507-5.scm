;;; $Id$
(comment "Don't close global variables")
(define (mkf y z) 
  (lambda (t) (+ g (+ y (+ z t)))) )
(set! g 500)
((mkf 7000 6)
 1 )

;;; end of u7507-5.scm
