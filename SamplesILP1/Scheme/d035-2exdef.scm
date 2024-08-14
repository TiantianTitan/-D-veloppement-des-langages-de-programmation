;;; $Id: d035-2exdef.scm 405 2006-09-13 17:21:53Z queinnec $
(comment "defined sur variable globale definie avant")
(expected-result #t)
(expected-printing "")

(begin
  (set! x 11)
  (defined x) )

;;; end of d035-2exdef.scm
