;;; $Id: u830-6.scm 405 2006-09-13 17:21:53Z queinnec $
(comment "appel methode predefinie: print")
(expected-result 830)
(expected-printing "<Point:x=11:y=22>")

(defclass Point Object
  (x y)
  (define (longueur)
    (+ (oget (self) "x") (oget (self) "y")) )
  (define (m1 z t)
    (+ (* z (oget (self) "x"))
       (* t (oget (self) "y")) ) ) )

(let ((point (new Point 11 22)))
  (send "print" point) )
830

;;; end of u830-6.scm
