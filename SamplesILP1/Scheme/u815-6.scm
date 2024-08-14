;;; $Id: u815-6.scm 405 2006-09-13 17:21:53Z queinnec $
(comment "Point + 1 methode zeroaire avec self")
(expected-result 812)

(defclass Point Object
  (x y)
  (define (longueur)
    (+ (oget (self) "x") (oget (self) "y")) ) )
(let ((point (new Point 810 002)))
  (send "longueur" point) )

;;; end of u815-6.scm
