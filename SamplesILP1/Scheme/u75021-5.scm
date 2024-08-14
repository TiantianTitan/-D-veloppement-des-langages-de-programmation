;;; $Id$
(comment "closure applied via global")
(let ((x 21))
  (set! g (lambda (y) (+ x y))) )
(g 75000)

;;; end of u75021-5.scm
