;;; $Id: u836-6.scm 405 2006-09-13 17:21:53Z queinnec $
(comment "methodes en parallele: interdit en compilation!")
(expected-result 836)

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
   (send "color2" pc)
   (lambda (e) #f)
   #f )
  836 )

;;; end of u836-6.scm
