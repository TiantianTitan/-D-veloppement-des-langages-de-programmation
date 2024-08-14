;;; $Id$
(comment "mutable closed variable")
(define (mkcounter start)
  (lambda ()
    (set! start (+ start 1))
    start ) )
(set! g (mkcounter 0))
(let ((a (g)))
  (let ((b (g)))
    (= b (+ a 1)) ) )

;;; end of u75088-5.scm
