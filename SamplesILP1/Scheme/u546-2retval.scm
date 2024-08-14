;;; $Id$
(comment "Erreur: retour trop specifie (a la Pascal)")
(expected-result #t)
(expected-printing "")

(define (quoi x y)
  (finalValue x)
  (finalValue y) )
(let ((step 1))
  (try-catch-finally
   (begin 
     (quoi 1 2)
     (set! step 2) )
   (lambda (exc)
     (set! step 3) )
   #f )
  (= step 3) )

;;; end of u546-2retval.scm
