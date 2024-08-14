;;; $Id: u822-6.scm 405 2006-09-13 17:21:53Z queinnec $
(comment "sous-classe PointColore sans methode, lecture/ecriture champ propre")
(expected-result 822)

(defclass Point Object
  (x y)
  (define (longueur)
    (+ (oget (self) "x") (oget (self) "y")) )
  (define (m1 z t)
    (+ (* z (oget (self) "x"))
       (* t (oget (self) "y")) ) ) )

(defclass PointColore Point
  (color) )
(let ((pc (new PointColore 11 22 "red")))
  (oset! pc "color" 822)
  (oget pc "color") )

;;; end of u822-6.scm
