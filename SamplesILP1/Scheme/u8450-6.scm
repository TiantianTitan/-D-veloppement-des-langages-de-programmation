;;; $Id$
(comment "field name to be mangled")
(expected-result 8450)
(defclass Point Object
  (x:x y)
  (define (print)
    (oget (self) x:x) ) )
(send "print" (new Point 8450 22))
;;; end of u8450-6.scm
