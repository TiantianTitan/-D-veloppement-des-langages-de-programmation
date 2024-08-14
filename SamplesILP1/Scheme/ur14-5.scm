;;; $Id$
(comment "une definition locale recursive utilisee")
(letrec ()
  (define (fact n)
    (if (> n 1) 
        (* n (fact (- n 1)))
        1 ) )
  (- (fact 4) 10) )

;;; end of ur14-5.scm
