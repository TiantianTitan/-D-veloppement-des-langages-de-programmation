;;; $Id: u5993-4gfv.scm 620 2007-01-07 14:24:50Z queinnec $
(comment "variable globale fonctionnelle et apply")
(define (deuxfois x)
  (* 2 x) )
(define (apply f x)
  (f x) )
(- (apply deuxfois 3000) 7)

;;; end of u5993-4trc.scm
