;;; $Id$
(comment "method name to be mangled")
(expected-result 8452)
(defclass Point Object
  (x y)
  (define (pr:int)
    8452 ) )
(send "pr:int" (new Point 11 22))
;;; end of u8452-6.scm
