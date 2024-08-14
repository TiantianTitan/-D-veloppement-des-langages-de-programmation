;;; $Id: u5995-4gfv.scm 637 2007-09-03 18:36:10Z queinnec $
(comment "variable globale fonctionnelle mutable")
(define (deuxfois x)
  (* 2 x) )
(let ((f deuxfois))
  (set! g f) )
(- (g 3000) 5)

;;; end of u5995-4trc.scm
