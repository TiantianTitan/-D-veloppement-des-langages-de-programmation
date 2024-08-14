;;; $Id: u841-6.scm 620 2007-01-07 14:24:50Z queinnec $
(comment "super(arg1) simple")
(expected-result 841)

(defclass Point Object
  (x)
  (define (m1 t)
    (* (oget (self) "x")
       t ) ) )

(defclass Point2D Point
  (y)
  (define (m1 t)
    (* (oget (self) "y")
       (super) ) ) )

(let ((pc (new Point2D 10 42)))
  (try-catch-finally
   (+ 1 (send "m1" pc 2))
   (lambda (e) #f)
   #f ) )


;;; end of u841-6.scm
