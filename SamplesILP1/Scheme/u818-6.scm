;;; $Id: u818-6.scm 405 2006-09-13 17:21:53Z queinnec $
(comment "2 methodes s'invoquant avec self")
(expected-result 15)

(defclass Point Object
  (x y)
  (define (m2 u)
    (+ (send "m1" (self) u u) 1) )
  (define (m1 z t)
    (+ (* z (oget (self) "x"))
       (* t (oget (self) "y")) ) ) )

(let ((point (new Point 3 4)))
  (send "m2" point 2) )

;;; end of u818-6.scm
