;;; $Id: u832-6.scm 405 2006-09-13 17:21:53Z queinnec $
(comment "Masquage d'une methode predefinie: print")
(expected-result 832)

(defclass Point Object
  (x y)
  (define (print)
    832 ) )

(send "print" (new Point 11 22))

;;; end of u832-6.scm
