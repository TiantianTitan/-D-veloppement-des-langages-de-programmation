;;; $Id: u10-4tr.scm 620 2007-01-07 14:24:50Z queinnec $
(comment "trace indirecte sur une fonction")
(expected-result 9)
(expected-printing "")

(define (f x y)
  (* x y) )
(define (g x)
  (let ((z (f x x))) ; trace
    (f z x) ) )      ; trace

(trace f)

(let ((un 1)
      (deux 2) )
  (+ un (g deux)) )

;;; end of u10-4tr.scm
