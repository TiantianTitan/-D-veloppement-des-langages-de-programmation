;;; $Id: u552-2.scm 405 2006-09-13 17:21:53Z queinnec $
(comment "Invocation en consequence")
(define (foo x)
  (* 2 x) )
(if #t 
    (foo 3)
    552 )

;;; end of u552-2.scm
