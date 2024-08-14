;;; $Id: u5901-2.scm 491 2006-10-09 10:33:08Z queinnec $
(comment "Affectation en position d'argument (sans conflit de variables)")

(define (foo x)
  (* 2 x) )

;;; Cette pseudo-sequence repose sur le fait que le compilateur et
;;; l'evaluateur evaluent les arguments de la gauche vers la droite.
(define (pseudosequence one two)
  two )

(let ((y 11))
  (foo (pseudosequence (set! y (+ y 1))
                        y )) )

;;; end of u5901-2.scm
