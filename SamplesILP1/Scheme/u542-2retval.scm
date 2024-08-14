;;; $Id$
(comment "appels croises (a la Pascal)")
(expected-result 33)

(define (moinsun x)
  (finalValue (- x 1)) )
;;; double est un mot cle de C!
(define (dou_ble x)
  (finalValue (* 2 x)) )

(moinsun (dou_ble (moinsun (dou_ble (moinsun 10)))))

;;; end of u542-2retval.scm
