;;; $Id: u821-6.scm 405 2006-09-13 17:21:53Z queinnec $
(comment "sous-classe PointColore sans methode mais allocation")
(expected-result 821)

(defclass Point Object
  (x y)
  (define (longueur)
    (+ (oget (self) "x") (oget (self) "y")) )
  (define (m1 z t)
    (+ (* z (oget (self) "x"))
       (* t (oget (self) "y")) ) ) )

(defclass PointColore Point
  (color) )
(new PointColore 11 22 "red")
821

;;; end of u821-6.scm
