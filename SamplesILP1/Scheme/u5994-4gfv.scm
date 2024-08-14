;;; $Id: u5994-4gfv.scm 637 2007-09-03 18:36:10Z queinnec $
(comment "variable globale fonctionnelle et twice")
(define (deuxfois x)
  (* 2 x) )
(define (twice f x)
  (f (f x)) )
(- (twice deuxfois 1500) 6)

;;; end of u5994-4trc.scm
