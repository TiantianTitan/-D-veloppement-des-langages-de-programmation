;;; $Id: u831-6.scm 405 2006-09-13 17:21:53Z queinnec $
(comment "appel methode predefinie: print sur sous-classe")
(expected-result 831)
(expected-printing "<PointColore:x=11:y=22:color=red>")

(defclass Point Object
  (x y)
  (define (longueur)
    (+ (oget (self) "x") (oget (self) "y")) )
  (define (m1 z t)
    (+ (* z (oget (self) "x"))
       (* t (oget (self) "y")) ) ) )

(defclass PointColore Point
  (color)
  (define (m2 x)
    (+ 823 x) ) )

(let ((pc (new PointColore 11 22 "red")))
  (send "print" pc) )
831

;;; end of u831-6.scm
