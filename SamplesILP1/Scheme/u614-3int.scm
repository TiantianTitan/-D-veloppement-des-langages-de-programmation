;;; $Id$
(comment "erreur dynamique sur let n-aire")
(expected-result 614)

(set! res 0)
(let ((f 2.78))
  (try-catch-finally
   (let ((i 1)
         (n (* 2 f))
         (f (* 3 f)) )
     (set! res 10) )
   (lambda (e) 
     (set! res 614) )
   #f ) )
res 

;;; end of u614-3int.scm
