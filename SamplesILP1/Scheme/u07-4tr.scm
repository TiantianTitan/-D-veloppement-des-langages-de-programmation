;;; $Id: u07-4tr.scm 620 2007-01-07 14:24:50Z queinnec $
(comment "trace enchassee sur une fonction")
(expected-result 30)
(expected-printing "")

(define (f x y)
  (* x y) )

(trace f)

(f 2 (f 3 5))

;;; end of u07-4tr.scm
