;;; $Id: e230-2esc.scm 405 2006-09-13 17:21:53Z queinnec $
(comment "Une boucle avec un next interne sautant une iteration")
(let ((i 1))
  (whileWithBreak (< i 10)
    (print i)
    (set! i (+ i 1))
    (if (= i 6) (next))
    (set! i (+ i 1)) ) )

;;; end of e230-2esc.scm
