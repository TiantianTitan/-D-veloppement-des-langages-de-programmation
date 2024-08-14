;;; $Id: u820-6.scm 405 2006-09-13 17:21:53Z queinnec $
(comment "sous-classe PointColore sans methode")
(expected-result 820)

(defclass Point Object
  (x y)
  (define (longueur)
    (+ (oget (self) "x") (oget (self) "y")) )
  (define (m1 z t)
    (+ (* z (oget (self) "x"))
       (* t (oget (self) "y")) ) ) )

(defclass PointColore Point
  (color) )
820

;;; end of u820-6.scm
