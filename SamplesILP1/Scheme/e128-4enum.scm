;;; $Id: e128-4enum.scm 405 2006-09-13 17:21:53Z queinnec $
(comment "bornes flottantes")
(let ((x 0))
  (try-catch-finally 
     (begin
       (foreach i (1.1 .. 3.3)
         (print i) )
       (set! x 1) )
     (lambda (exc)
       (set! x 2) )
     3 )
  (= x 1) )

;;; end of e128-4enum.scm
