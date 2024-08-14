;;; $Id$
(comment "double appel a fonction globale et code inutile (a la Pascal)")
(expected-result 541)
(expected-printing "(OKOK)(OKOK)")

(define (moinsun x)
  (print "(OK") 
  (finalValue (- x 1))
  (print "OK)") )

(moinsun (moinsun 543))

;;; end of u5411-2.scm
