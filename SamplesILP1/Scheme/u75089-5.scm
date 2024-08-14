;;; $Id$
(comment "mutable closed variable")
(define (mkcounter start)
  (lambda ()
    (set! start (+ start 1))
    start ) )
(let ((c1 (mkcounter 0))
      (c2 (mkcounter 10)) )
  (c1) (c2) (c2) (c1) (c2)
  (= (+ 11 (c1)) (c2)) )

;;; end of u75089-5.scm
