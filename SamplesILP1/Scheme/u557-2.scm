;;; $Id: u557-2.scm 405 2006-09-13 17:21:53Z queinnec $
(comment "deux fonctions non mutuellement recursives dans le mauvais ordre")
(define (bar y)
  (+ 3 (foo y)) )
(define (foo x)
  (* 2 x) )
(bar 557)

;;; end of u557-2.scm
