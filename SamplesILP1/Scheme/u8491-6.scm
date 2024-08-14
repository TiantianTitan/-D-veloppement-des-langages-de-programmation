;;; $Id: u8491-6.scm 620 2007-01-07 14:24:50Z queinnec $
(comment "super.print(arg1) double")
(expected-result 8491)
(expected-printing "print@Point2Dprint@Point<Point2D:x=8491:y=1>")

(defclass Point Object
  (x)
  (define (print)
    (print "print@Point")
    (super) ) )

(defclass Point2D Point
  (y)
  (define (print)
    (print "print@Point2D")
    (super) ) )

(let ((pc (new Point2D 8491 1)))
  (try-catch-finally
   (begin (send "print" pc)
          8491 )
   (lambda (e) #f)
   #f ) )

;;; end of u8491-6.scm
