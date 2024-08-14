;;; $Id: u68-3.scm 405 2006-09-13 17:21:53Z queinnec $
(comment "exception dans catcher")
(let ((x 1))
  (try-catch-finally
    (try-catch-finally
      (begin 
        (throw 4)
        (set! x 44) )
      (lambda (exc)
        (throw (* 2 exc)) )
      (set! x (* 3 x)) )
    (lambda (exc)
      (set! x (* 5 (* x exc))) )
    #f )
  (print x)
  (= x 120) )

;;; end of u68-3.scm
