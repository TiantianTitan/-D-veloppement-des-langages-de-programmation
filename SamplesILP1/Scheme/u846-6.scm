;;; $Id: u846-6.scm 620 2007-01-07 14:24:50Z queinnec $
(comment "super(arg1 arg2) interrompu avec renommage")
(expected-result 846)

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
    (oget (self) "z") ) )

(defclass Point4D Point3D
  (t)
  (define (m1 tt uu)
    (+ (oget (self) "t")
       (super) ) ) ) 

(let ((pc4 (new Point4D -1 -2 400 16))
      (pc2 (new Point2D 20 1)) )
  (try-catch-finally
   (+ (send "m1" pc4 -3 -4)   ; 416
      (send "m1" pc2 20 30) ) ; 430 
   (lambda (e) #f)
   #f ) )

;;; end of u846-6.scm
