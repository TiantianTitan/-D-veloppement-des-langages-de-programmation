;;; $Id: u817-6.scm 405 2006-09-13 17:21:53Z queinnec $
(comment "Point + 2 methodes avec self")
(expected-result 19)

(defclass Point Object
  (x y)
  (define (longueur)
    (+ (oget (self) "x") (oget (self) "y")) )
  (define (m1 z t)
    (+ (* z (oget (self) "x"))
       (* t (oget (self) "y")) ) ) )
(let ((point (new Point 2 3)))
  (send "m1" point 2 (send "longueur" point)) )

;;; end of u817-6.scm
