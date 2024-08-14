;;; $Id: u59015-2.scm 620 2007-01-07 14:24:50Z queinnec $
(comment "Affectation en position d'argument (et conflit noms de variables)")

(define (foo x)
  (* 2 x) )

;;; Cette pseudo-sequence repose sur le fait que le compilateur et
;;; l'evaluateur evaluent les arguments de la gauche vers la droite.
(define (pseudosequence one two)
  two )

(let ((x 11)) ; Ici on choisit le nom x pour provoquer un conflit
  (foo (pseudosequence (set! x (+ x 1))
                        x )) )

;;; end of u59015-2.scm
