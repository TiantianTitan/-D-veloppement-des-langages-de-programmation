;;; $Id: u816-6.scm 405 2006-09-13 17:21:53Z queinnec $
(comment "Point + 1 methode binaire avec self")
(expected-result 13)

(defclass Point Object
  (x y)
  (define (m1 z t)
    (+ (* z (oget (self) "x"))
       (* t (oget (self) "y")) ) ) )
(let ((point (new Point 2 3)))
  (send "m1" point 2 3) )

;;; end of u816-6.scm
