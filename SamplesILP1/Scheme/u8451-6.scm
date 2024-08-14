;;; $Id$
(comment "class name to be mangled")
(expected-result 8451)
(defclass Po:int Object
  (x y)
  (define (print)
    8451 ) )
(send "print" (new Po:int 11 22))
;;; end of u8451-6.scm
