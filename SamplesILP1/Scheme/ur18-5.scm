;;; $Id$
(comment "fermeture utilisee en dehors de sa portee native")
(+ ((letrec ()
      (define (double x) (* 2 x))
      double )
    8 )
   2 )

;;; end of ur18-5.scm
