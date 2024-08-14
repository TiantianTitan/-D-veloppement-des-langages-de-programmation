;;; $Id: e240-2esc.scm 405 2006-09-13 17:21:53Z queinnec $
(comment "Une boucle avec un last interne")
(let ((i 1))
  (whileWithBreak (< i 10)
    (set! i (+ i 1))
    (print i)
    (if (= i 6) (last))
    (print i) ) )

;;; end of e240-2esc.scm
