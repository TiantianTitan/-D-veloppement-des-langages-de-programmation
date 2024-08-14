;;; $Id: u05-4tr.scm 620 2007-01-07 14:24:50Z queinnec $
(comment "trace sur une fonction")
(expected-result 6)
(expected-printing "")

(define (f x y)
  (* x y) )

(trace f)

(f 2 3)

;;; end of u05-4tr.scm
