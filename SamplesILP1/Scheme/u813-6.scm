;;; $Id: u813-6.scm 405 2006-09-13 17:21:53Z queinnec $
(comment "Point + 1 methode binaire sans self")
(expected-result 813)

(defclass Point Object
  (x y)
  (define (m1 z t)
    (+ z t) ) )
(let ((point (new Point 11 22)))
  (send "m1" point 800 13) )

;;; end of u813-6.scm
