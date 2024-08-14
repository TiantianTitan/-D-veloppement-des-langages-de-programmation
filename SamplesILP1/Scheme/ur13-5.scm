;;; $Id$
(comment "une definition locale non recursive utilisee")
(letrec ()
  (define (double x) (+ x x))
  (print "ok")
  (+ 1 (double 6)) )

;;; end of ur13-5.scm
