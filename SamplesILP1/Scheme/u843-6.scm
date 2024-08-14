;;; $Id: u843-6.scm 620 2007-01-07 14:24:50Z queinnec $
(comment "super(arg1 arg2) avec une classe entre")
(expected-result 843)

(defclass Point Object
  (x)
  (define (m1 t u)
    (+ u (oget (self) "x"))) )

(defclass Point2D Point
  (y) )

(defclass Point3D Point2D
  (z)
  (define (m1 t u)
    (+ (oget (self) "y")
       (+ t
          (* (oget (self) "z")
             (super) ) ) ) ) )

(let ((pc (new Point3D 8 -1 84)))
  (try-catch-finally
   (send "m1" pc 4 2) ; -1 + 4 + 84*(2+8)
   (lambda (e) #f)
   #f ) )

;;; end of u843-6.scm
