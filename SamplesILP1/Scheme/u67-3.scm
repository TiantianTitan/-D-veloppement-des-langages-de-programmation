;;; $Id: u67-3.scm 405 2006-09-13 17:21:53Z queinnec $
(comment "tous les catchers tournent")
(let ((x 1))
  (try-catch-finally
    (begin 
      (set! x (+ x 1))
      (try-catch-finally
        (begin 
          (set! x (+ x 1))
          (throw x) )
        (lambda (exc)
          (print x)(print exc)
          (set! x (* 2 (* x exc)))
          (throw x) )
        #f ) )
    (lambda (exc)
      (print x)(print exc)
      (set! x (* 3 (* x exc))) )
    #f )
  (= x 972) )

;;; end of u67-3.scm
