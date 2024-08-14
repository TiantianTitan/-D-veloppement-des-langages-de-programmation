;;; $Id: u56-2.scm 405 2006-09-13 17:21:53Z queinnec $
(comment "deux fonctions mutuellement recursives")
(define (odd n)
  (if (= n 0) 
      #f
      (if (= n 1)
          #t 
          (even (- n 1)) ) ) )
(define (even n)
  (if (= n 0)
      #t 
      (if (= n 1)
          #f
          (odd (- n 1)) ) ) )
(not (odd 56))

;;; end of u56-2.scm
