;;; $Id: u59025-2.scm 620 2007-01-07 14:24:50Z queinnec $
(comment "affectation et invocation en position d'arguments (avec conflit)")

;;; Cette pseudo-sequence repose sur le fait que le compilateur et
;;; l'evaluateur evaluent les arguments de la gauche vers la droite.
(define (pseudosequence one two)
  two )

(define (foo x)
  (* 2 x) )

(let ((x 12))
  (foo (pseudosequence (set! x (foo x))
                       x ) ) )

;;; end of u5902.scm
