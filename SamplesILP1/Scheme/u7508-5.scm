;;; $Id$
(comment "mutable closed variable")
(let ((n 7507))
  ((lambda () 
     (set! n (+ n 1))
     n ))
)

;;; end of u7508-5.scm
