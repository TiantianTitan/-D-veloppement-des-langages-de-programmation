;;; $Id$
(comment "fonctions recursives (a la Pascal)")
(expected-result 24)

(define (fact n)
  (if (< n 2)
      (finalValue 1)
      (finalValue (* n (fact (- n 1)))) ) )
(fact 4)

;;; end of u543-2retval.scm
