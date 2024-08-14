;;; $Id: u62-3.scm 405 2006-09-13 17:21:53Z queinnec $
(comment "le finally tourne meme s'il y a une exception depuis le corps")
(let ((x 1))
  (try-catch-finally
    (throw 3)
    #f
    (set! x 4) )
  (print x)
  (= x 4) )

;;; end of u62-3.scm
