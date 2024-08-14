;;; $Id: u20-4tr.scm 620 2007-01-07 14:24:50Z queinnec $
(comment "trace sur fonction globale muable")
(expected-result 20)
(expected-printing "2215")

(define (f1 x)
  (* 11 x) )
(define (f2 y)
  (* 5 y) )

(trace f1)

(print (f1 2))
(set! f1 f2)
(print (f1 3))
20

;;; end of u20-4tr.scm
