;;; $Id$
;;; Utile pour compter le nombre d'allocations (en C)

(let ((n 0))
  (while (< n 10)
     (set! n (+ n 1)) )
  (print n) )

;;; end of mem-3.scm
