;;; $Id: e220-2esc.scm 405 2006-09-13 17:21:53Z queinnec $
(comment "Une boucle avec un next interne")
(let ((i 1))
  (whileWithBreak (< i 5)
    (print i)
    (set! i (+ i 1))
    (next)
    (set! i (+ i 1)) ) )

;;; end of e220-2esc.scm
