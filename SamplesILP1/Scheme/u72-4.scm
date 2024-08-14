;;; $Id: u72-4.scm 405 2006-09-13 17:21:53Z queinnec $
(comment "alternative et bloc local en argument")
(define (foo x y)
  (+ x y) )
(foo (if #t 8 1)
     (let ((x 8)) (* x x)) )

;;; end of u72-4.scm
