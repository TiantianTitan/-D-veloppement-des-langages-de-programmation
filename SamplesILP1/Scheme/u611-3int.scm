;;; $Id$
(comment "erreur dynamique sur let unaire")
(expected-result 611)

(set! res 0)
(let ((f 2.78))
  (try-catch-finally
   (let ((n (* 2 f)))
     (set! res 10) )
   (lambda (e) 
     (set! res 611) )
   #f )
  res )

;;; end of u611-3int.scm
