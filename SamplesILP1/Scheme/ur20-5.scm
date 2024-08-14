;;; $Id$
(comment "fermeture recursive utilisee en dehors de sa portee native")
(- ((letrec ()
      (define (fact n) (if (<= n 1) 1 (* n (fact (- n 1)))))
      fact )
    4 )
   4 )

;;; end of ur20-5.scm
