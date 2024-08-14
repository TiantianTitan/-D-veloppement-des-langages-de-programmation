;;; $Id: u825-6.scm 405 2006-09-13 17:21:53Z queinnec $
(comment "sous-classe PointColore avec methode, appel methode heritee")
(expected-result 825)

(defclass Point Object
  (x y)
  (define (longueur)
    (+ (oget (self) "x") (oget (self) "y")) )
  (define (m1 z t)
    (+ (* z (oget (self) "x"))
       (* t (oget (self) "y")) ) ) )

(defclass PointColore Point
  (color)
  (define (m2)
    824 ) )

(let ((pc (new PointColore 820 005 "red")))
  (send "m1" pc 1 1) )

;;; end of u825-6.scm
