;;; $Id$
(comment "double appel a fonction globale (a la Pascal)")
(expected-result 541)

(define (moinsun x)
  (finalValue (- x 1)) )

(moinsun (moinsun 543))

;;; end of u541-2.scm
