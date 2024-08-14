;;; $Id: d020-2exdef.scm 405 2006-09-13 17:21:53Z queinnec $
(comment "exists sur une variable globale definie avant")
(expected-result #t)
(expected-printing "")

(begin
  (set! foo 1)
  (exists foo) )

;;; end of d020-2exdef.scm
