;;; $Id: u53-2.scm 405 2006-09-13 17:21:53Z queinnec $
(comment "double boucle imbriquee")
(let ((x 5))
  (while (< x 53)
    (print x)
    (set! x (* 2 x))
    (while (> x 53)
      (print x)
      (set! x (- x 3)) ) )
  x )

;;; end of u53-2.scm
