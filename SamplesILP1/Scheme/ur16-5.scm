;;; $Id$
(comment "deux definitions locales non mutuellement recursives, utilisees")
(letrec ()
  (define (fact n)
    (if (> n 1) 
        (* n (fact (- n 1)))
        1 ) )
  (define (double x) (* 2 x))
  (+ (double (fact 3)) 4) )

;;; end of ur16-5.scm
