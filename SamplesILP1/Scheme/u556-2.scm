;;; $Id: u556-2.scm 405 2006-09-13 17:21:53Z queinnec $
(comment "deux fonctions non mutuellement recursives dans le bon ordre")
(define (foo x)
  (* 2 x) )
(define (bar y)
  (+ 3 (foo y)) )
(bar 556)

;;; end of u556-2.scm
