;;; $Id$
(comment "Exemple examen (a la Pascal)")
(expected-result 6)
(expected-printing "OK")

(define (moinsun x)
  (finalValue (- x 1)) )
(define (quoi x y)
  (+ x y) )
(define (fact n)
  (if (< n 2)
      (begin
        (finalValue 1)
        (print "OK") )
      (finalValue (* n (fact (moinsun n)))) ) )
(fact 3)

;;; end of u544-2retval.scm
