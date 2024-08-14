;;; $Id$
(comment "erreur dynamique sur affectation")
(expected-result 612)

(set! res 0)
(let ((j 2))
  (try-catch-finally
   (set! j 1.01)
   (lambda (e) 
     (set! j 612) )
   #f )
  j )

;;; end of u612-3int.scm
