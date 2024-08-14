;;; $Id: u8492-6.scm 620 2007-01-07 14:24:50Z queinnec $
(comment "super.print(arg1) double")
(expected-result 8492)
(expected-printing "print@Point2D<Point2D:x=8492:y=1>")

(defclass Point Object
  (x) )

(defclass Point2D Point
  (y)
  (define (print)
    (print "print@Point2D")
    (super) ) )

(let ((pc (new Point2D 8492 1)))
  (try-catch-finally
   (begin (send "print" pc)
          8492 )
   (lambda (e) #f)
   #f ) )

;;; end of u8492-6.scm
