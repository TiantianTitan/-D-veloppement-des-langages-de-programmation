;;; $Id: u826-6.scm 405 2006-09-13 17:21:53Z queinnec $
(comment "sous-classe PointColore avec methode propre, sans self, masquante")
(expected-result 826)

(defclass Point Object
  (x y)
  (define (longueur)
    (+ (oget (self) "x") (oget (self) "y")) )
  (define (m1 z t)
    (+ (* z (oget (self) "x"))
       (* t (oget (self) "y")) ) ) )

(defclass PointColore Point
  (color)
  (define (longueur)
    826 ) )

(let ((pc (new PointColore 11 22 "red")))
  (send "longueur" pc) )

;;; end of u826-6.scm
