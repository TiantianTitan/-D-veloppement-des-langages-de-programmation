;;; $Id$
(comment "erreur dynamique sur catch")
(expected-result 615)

(set! res 0)
(try-catch-finally
 (try-catch-finally
  (throw 2.3)
  (lambda (n) ; n <- 2.3 !!!
    (set! res 1) )
  #f )
 (lambda (e)
   (set! res 615) )
 #f )
res

;;; end of u615-3int.scm
