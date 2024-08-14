;;; $Id: u5902-2.scm 491 2006-10-09 10:33:08Z queinnec $
(comment "affectation et invocation en position d'arguments (sans conflit)")

;;; Cette pseudo-sequence repose sur le fait que le compilateur et
;;; l'evaluateur evaluent les arguments de la gauche vers la droite.
(define (pseudosequence one two)
  two )

(define (foo x)
  (* 2 x) )

(let ((y 12))
  (foo (pseudosequence (set! y (foo y))
                       y ) ) )

;;; end of u5902.scm
