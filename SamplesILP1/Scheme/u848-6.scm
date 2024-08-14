;;; $Id: u848-6.scm 620 2007-01-07 14:24:50Z queinnec $
(comment "super(arg1 arg2) interrompu avec double hierarchie avec renommage")
(expected-result 848)

(defclass Point Object
  (x)
  (define (m1 tt uu)
    (* tt (oget (self) "x"))) )

(defclass Point2Da Point
  (y)
  (define (m1 ttt uuu)
    (* uuu (oget (self) "y")) ) )

(defclass Point3Da Point2Da
  (z)
  (define (m1 tt uu)
    (+ (oget (self) "z")
       (super) ) ) ) 

(defclass Point2Db Point
  (yy)
  (define (m1 t u)
    (* u (oget (self) "yy")) ) )

(defclass Point3Db Point2Db
  (zz)
  (define (m1 tt uu)
    (* (oget (self) "zz")
       (super) ) ) ) 

(let ((pc4 (new Point3Da -2 100 23))
      (pc2 (new Point3Db 20 10 5)) )
  (try-catch-finally
   (+ 275                           ; 275
      (+ (send "m1" pc4 3 4)        ; 23 + 4*100
         (send "m1" pc2 2 3) ) )    ; 5*3*10
   (lambda (e) #f)
   #f ) )

;;; end of u848-6.scm
