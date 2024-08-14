;;; $Id: u554-2.scm 405 2006-09-13 17:21:53Z queinnec $
(comment "invocation en alternatives imbriquees")
(define (foo x)
  (* x 2) )
(if (= 1 2)
    554
    (if (= 2 3)
        -554
        (foo 554) ) )

;;; end of u554-2.scm
