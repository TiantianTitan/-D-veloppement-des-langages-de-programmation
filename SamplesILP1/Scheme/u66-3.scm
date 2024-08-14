;;; $Id: u66-3.scm 405 2006-09-13 17:21:53Z queinnec $
(comment "tous les finally tournent")
(let ((x 0))
  (try-catch-finally
    (begin 
      (set! x (+ x 1))
      (try-catch-finally
        (begin 
          (set! x (+ x 10))
          (throw x) )
        #f
        (begin
          (print x)
          (set! x (* 2 x)) ) ) )
    #f
    (begin 
      (print x)
      (set! x (* 3 x)) ) )
  (= x 66) )

;;; end of u66-3.scm
