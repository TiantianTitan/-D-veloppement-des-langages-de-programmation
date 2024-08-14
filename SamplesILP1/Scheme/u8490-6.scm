;;; $Id: u8490-6.scm 620 2007-01-07 14:24:50Z queinnec $
(comment "super.print(arg1) simple")
(expected-result 8490)
(expected-printing "print@Point<Point:x=8490>")

(defclass Point Object
  (x)
  (define (print)
    (print "print@Point")
    (super) ) )

(let ((pc (new Point 8490)))
  (try-catch-finally
   (begin (send "print" pc)
          8490 )
   (lambda (e) #f)
   #f ) )

;;; end of u8490-6.scm
