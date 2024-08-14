;;; $Id: u828-6.scm 405 2006-09-13 17:21:53Z queinnec $
(comment "Superclasse sans champ")
(expected-result 828)

(defclass Point Object
  () )

(defclass SousPoint Point
  (sous)
  (define (m1)
    (oget (self) "sous") ) )

(send "m1" (new SousPoint 828))

;;; end of u828-6.scm
