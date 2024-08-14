;;; $Id$
(comment "double fermeture recursive utilisee en dehors de sa portee native")
(if ((letrec ()
       (define (odd n)
         (if (= n 0) #f (even (- n 1))) )
       (define (even n)
         (if (<= n 0) #t (odd (- n 1))) )
       even )
     4 )
    21
    #f )

;;; end of ur21-5.scm
