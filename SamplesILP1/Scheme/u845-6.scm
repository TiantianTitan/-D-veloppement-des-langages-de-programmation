;;; $Id: u845-6.scm 620 2007-01-07 14:24:50Z queinnec $
(comment "super(arg1 arg2) double avec renommage")
(expected-result 845)

(defclass Point Object
  (x)
  (define (m1 tt uu)
    (* tt (oget (self) "x"))) )

(defclass Point2D Point
  (y)
  (define (m1 ttt uuu)
    (+ (* uuu (oget (self) "y"))
       (super) ) ) )

(defclass Point3D Point2D
  (z)
  (define (m1 t u)
    (+ (oget (self) "z")
       (super) ) ) )

(let ((pc (new Point3D 400 10 15)))
  (try-catch-finally
   (send "m1" pc 2 3)
   (lambda (e) #f)
   #f ) )

;;; end of u845-6.scm
