;;; $Id$
(comment "fermetures utilisees en dehors de sa portee native")
(+ ((letrec ()
      (define (quadruple n) (double (double n)))
      (define (double x) (* 2 x))
      quadruple )
    4 )
   3 )

;;; end of ur19-5.scm
