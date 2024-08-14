;;; $Id: u824-6.scm 405 2006-09-13 17:21:53Z queinnec $
(comment "sous-classe PointColore avec methode propre sans self")
(expected-result 824)

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
  (send "m2" pc 1) )

;;; end of u824-6.scm
