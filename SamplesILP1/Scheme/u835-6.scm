;;; $Id: u835-6.scm 405 2006-09-13 17:21:53Z queinnec $
(comment "methodes en parallele: interdit en compilation!")
(expected-result 835)

(defclass Point Object
  (x y)
  (define (x) 
    (oget (self) "x") ) )

(defclass PointColore Point
  (color)
  (define (color1)
    (oget (self) "color") ) )

(defclass PointLarge Point
  (taille)
  (define (color2)
    (oget (self) "taille") ) )

(let ((pc (new PointColore 11 22 "red"))
      (pl (new PointLarge  33 44 835)) )
  (try-catch-finally 
   (send "color1" pl)
   (lambda (e) #f)
   #f )
  835 )

;;; end of u835-6.scm
