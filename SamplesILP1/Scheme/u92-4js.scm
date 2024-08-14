;;; $Id$
(comment "un petit programme utilisant presque tout de javascript")

(define (syracuse x)
  (let ((count 0))
    (try-catch-finally
     (begin 
       (while (> x 1)
          (set! count (+ count 1))
          (if (= 1 (modulo x 2))
              (set! x (+ 1 (* 3 x)))
              (set! x (/ x 2)) ) )
       x )
     (lambda (pb) (throw count))
     (set! globalcount (+ globalcount count)) ) ) )

(set! globalcount 0)
(print (syracuse 23))
(print globalcount)
(print (syracuse (- (syracuse 23) 1)))
(print globalcount)

;;; end of u92-4js.scm
