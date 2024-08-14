;;; $Id: e125-4enum.scm 405 2006-09-13 17:21:53Z queinnec $
(comment "bornes non numeriques (stop: bool)")
(let ((x 0))
  (try-catch-finally 
     (begin
       (foreach i (1 .. #t)
         (print i) )
       (set! x 1) )
     (lambda (exc)
       (set! x 2) )
     3 )
  (= x 2) )

;;; end of e125-4enum.scm
