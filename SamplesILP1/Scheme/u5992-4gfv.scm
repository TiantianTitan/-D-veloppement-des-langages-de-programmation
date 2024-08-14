;;; $Id: u5992-4gfv.scm 620 2007-01-07 14:24:50Z queinnec $
(comment "variable globale fonctionnelle recopiee")
(define (deuxfois x)
  (* 2 x) )
(let ((f deuxfois))
  (- (f 3000) 8) )

;;; end of u5992-4trc.scm
