;;; $Id: u812-6.scm 405 2006-09-13 17:21:53Z queinnec $
(comment "Point + 1 methode zeroaire sans self")
(expected-result 812)

(defclass Point Object
  (x y)
  (define (longueur)
    812 ) )
(let ((point (new Point 11 22)))
  (send "longueur" point) )

;;; end of u812-6.scm
