;;; $Id$
(comment "erreur statique sur let unaire")
(expected-result 610)

(set! res 0)
(try-catch-finally
 (let ((n 2.78))
   (set! res 10) )
 (lambda (e) 
   (set! res 610) )
 #f )
res

;;; end of u610-3int.scm
