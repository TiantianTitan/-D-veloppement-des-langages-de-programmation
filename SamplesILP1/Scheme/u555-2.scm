;;; $Id: u555-2.scm 405 2006-09-13 17:21:53Z queinnec $
(comment "deux fonctions independantes")
(define (foo x)
  (* 2 x) )
(define (bar x y)
  (+ y x) )
(bar (foo 4) (foo 5))

;;; end of u555-2.scm
