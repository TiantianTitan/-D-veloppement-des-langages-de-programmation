;;; $Id: u840-6.scm 620 2007-01-07 14:24:50Z queinnec $
(comment "super() simple")
(expected-result 840)

(defclass Point Object
  (x)
  (define (m1)
    (oget (self) "x")) )

(defclass Point2D Point
  (y)
  (define (m1)
    (* (oget (self) "y")
       (super) ) ) )

(let ((pc (new Point2D 10 84)))
  (try-catch-finally
   (send "m1" pc)
   (lambda (e) #f)
   #f ) )

;;; end of u840-6.scm
