;;; $Id: u844-6.scm 620 2007-01-07 14:24:50Z queinnec $
(comment "super(arg1 arg2) double et renommage")
(expected-result 844)

(defclass Point Object
  (x)
  (define (m1 u t)
    (* u (oget (self) "x"))) )

(defclass Point2D Point
  (y)
  (define (m1 t u)
    (+ (* u (oget (self) "y"))
       (super) ) ) )

(defclass Point3D Point2D
  (z)
  (define (m1 t u)
    (+ (oget (self) "z")
       (super) ) ) )

(let ((pc (new Point3D 400 10 14)))
  (try-catch-finally
   (send "m1" pc 2 3) ; 14 + 3*10 + 2*400
   (lambda (e) #f)
   #f ) )

;;; end of u844-6.scm
