;;; $Id: u814-6.scm 405 2006-09-13 17:21:53Z queinnec $
(comment "Point + 2 methodes sans self")
(expected-result 814)

(defclass Point Object
  (x y)
  (define (longueur)
    812 )
  (define (m1 z t)
    (+ z t) ) )
(let ((point (new Point 11 22)))
  (send "m1" point 2 (send "longueur" point)) )

;;; end of u814-6.scm
