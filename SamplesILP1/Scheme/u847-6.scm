;;; $Id: u847-6.scm 620 2007-01-07 14:24:50Z queinnec $
(comment "super(arg1 arg2) double avec double hierarchie avec renommage")
(expected-result 847)

(defclass Point Object
  (x)
  (define (m1 tt uu)
    (* tt (oget (self) "x"))) )

(defclass Point2Da Point
  (y)
  (define (m1 ttt uuu)
    (+ (* uuu (oget (self) "y"))
       (super) ) ) )

(defclass Point3Da Point2Da
  (z)
  (define (m1 tt uu)
    (+ (oget (self) "z")
       (super) ) ) ) 

(defclass Point2Db Point
  (yy)
  (define (m1 t u)
    (* (* u (oget (self) "yy"))
       (super) ) ) )

(defclass Point3Db Point2Db
  (zz)
  (define (m1 tt uu)
    (* (oget (self) "zz")
       (super) ) ) ) 

(let ((pc4 (new Point3Da -2 100 23))
      (pc2 (new Point3Db 20 1 5)) )
  (try-catch-finally
   (+ (send "m1" pc4 3 4)    ; 417 = 17 + 4*100 + 3*-2
      (+ (send "m1" pc2 2 3) ; 600 = 5*3*1*2*20
         -170 ) )
   (lambda (e) #f)
   #f ) )

;;; end of u847-6.scm
