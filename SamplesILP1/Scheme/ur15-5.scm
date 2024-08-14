;;; $Id$
(comment "deux definitions locales non mutuellement recursives")
(letrec ()
  (define (fact n)
    (if (> n 1) 
        (* n (fact (- n 1)))
        1 ) )
  (define (double x) (* 2 x))
  (+ (fact 3) 9) )

;;; end of ur15-5.scm
