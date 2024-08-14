;;; $Id: u842-6.scm 620 2007-01-07 14:24:50Z queinnec $
(comment "super() avec une classe entre")
(expected-result 842)

(defclass Point Object
  (x)
  (define (m1)
    (oget (self) "x")) )

(defclass Point2D Point
  (y) )

(defclass Point3D Point2D
  (z)
  (define (m1)
    (+ (oget (self) "y")
       (* (oget (self) "z")
          (super) ) ) ) )

(let ((pc (new Point3D 10 2 84)))
  (try-catch-finally
   (send "m1" pc)
   (lambda (e) #f)
   #f ) )

;;; end of u842-6.scm
