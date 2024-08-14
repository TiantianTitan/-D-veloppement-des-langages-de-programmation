;;; $Id$
(comment "lecture d'une variable globale non initialisee")
(define (foo x)
  (set! g x) )

(try-catch-finally
 g
 (lambda (e)
   (print "OK") )
 #f )

;;; end of u601-3.scm
