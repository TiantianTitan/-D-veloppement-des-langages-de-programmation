;;; $Id: u5991-4gfv.scm 620 2007-01-07 14:24:50Z queinnec $
(comment "variable globale fonctionnelle et invocation calculee")
(define (deuxfois x)
  (* 2 x) )
(- ((if deuxfois deuxfois deuxfois) 3000) 9)

;;; end of u5991-4trc.scm
