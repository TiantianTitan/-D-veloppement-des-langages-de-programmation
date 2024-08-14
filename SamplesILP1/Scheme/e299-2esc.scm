;;; $Id: e299-2esc.scm 405 2006-09-13 17:21:53Z queinnec $
(comment "exemple de l'examen")
(let ((i 1))
  (whileWithBreak (< i 20 )
     (set! i (+ i 1))
     (if (= (modulo i 2) 0)
         (next) )
     (print i)
     (if (> i 10)
         (last) ) ) )
; Ce programme devrait imprimer 357911

;;; end of e299-2esc.scm
