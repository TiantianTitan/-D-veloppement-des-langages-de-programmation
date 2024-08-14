;;; $Id$
(comment "mutable closed variable out of scope")
((let ((n 75080))
   (lambda () 
     (set! n (+ n 1))
     n ) ))

;;; end of u75081-5.scm
