;;; $Id: u69-3.scm 405 2006-09-13 17:21:53Z queinnec $
(comment "exception dans finally")
(let ((x 1))
  (try-catch-finally
    (try-catch-finally
      (throw 11)
      (lambda (exc)
        (print exc) )
      (begin 
        (throw (* 3 x))
        (set! x 55) ) )
    #f
    (print x) )
  (print x)
  (= x 1) )

;;; end of u69-3.scm
