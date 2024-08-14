;;; $Id$
(comment "mutable closed variable")
(let ((n 5))
  ((lambda () 
     (set! n (+ n 1))
     n ))
)

;;; end of u75085-5.scm
