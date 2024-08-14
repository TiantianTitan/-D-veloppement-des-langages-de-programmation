;;; $Id: e260-2esc.scm 405 2006-09-13 17:21:53Z queinnec $
(comment "Deux boucles anonymes")
(let ((i 1))
  (whileWithBreak (< i 10)
    (set! i (+ i 1))
    (print i)
    (let ((j 1))
      (whileWithBreak (< j i)
        (print j)
        (set! j (* j 2)) ) ) ) )

;;; end of e260-2esc.scm
