;;; $Id: e126-4enum.scm 405 2006-09-13 17:21:53Z queinnec $
(comment "bornes non numeriques (start: string)")
(let ((x 0))
  (try-catch-finally 
     (begin
       (foreach i ("foo" .. 3)
         (print i) )
       (set! x 1) )
     (lambda (exc)
       (set! x 2) )
     3 )
  (= x 2) )

;;; end of e126-4enum.scm
