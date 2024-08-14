;;; $Id$
(comment "une definition locale non recursive et non utilisee")
(letrec ()
  (define (double x) (+ x x))
  (print "ok")
  12 )

;;; end of ur11-5.scm
