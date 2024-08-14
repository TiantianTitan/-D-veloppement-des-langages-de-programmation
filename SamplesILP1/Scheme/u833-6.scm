;;; $Id: u833-6.scm 405 2006-09-13 17:21:53Z queinnec $
(comment "Masquage d'une methode predefinie: print sur sous-classe")
(expected-result 833)

(defclass Point Object
  (x y)
  (define (print)
    832 ) )

(defclass PointColore Point 
  (color)
  (define (print)
    833 ) )

(send "print" (new PointColore 11 22 "red"))

;;; end of u833-6.scm
