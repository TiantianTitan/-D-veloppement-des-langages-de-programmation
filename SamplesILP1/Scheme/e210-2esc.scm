;;; $Id: e210-2esc.scm 405 2006-09-13 17:21:53Z queinnec $
(comment "Une boucle avec un next superflu")
(let ((i 1))
  (whileWithBreak (< i 5)
    (print i)
    (set! i (+ i 1))
    (next) ) )

;;; end of e210-2esc.scm
