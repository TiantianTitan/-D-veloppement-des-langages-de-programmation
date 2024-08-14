;;; $Id: u827-6.scm 405 2006-09-13 17:21:53Z queinnec $
(comment "perpetuation de self a travers la superclasse")
(expected-result 827)

(defclass Point Object
  (x y)
  (define (m2 u)
    (send "m1" (self) 1 u) )
  (define (m1 z t)
    (+ (* z (oget (self) "x"))
       (* t (oget (self) "y")) ) ) )

(defclass PointColore Point
  (color)
  (define (m1 z t)
    (+ 826 z) ) )

(let ((pc (new PointColore 11 22 "red")))
  (send "m2" pc 2) )

;;; end of u827-6.scm
