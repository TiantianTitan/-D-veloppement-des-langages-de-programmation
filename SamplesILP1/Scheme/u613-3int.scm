;;; $Id$
(comment "erreur dynamique sur invocation")
(expected-result 613)

(define (foo n)
  (set! res (* 2 n)) )
(set! res 0)
(try-catch-finally
 (begin 
   (foo 3)
   (foo (* 2 2.5)) ;; Incorrect
   (foo 10) )
 (lambda (e)
   (foo 305) )
 #f )
(+ res 3)

;;; end of u613-3int.scm
