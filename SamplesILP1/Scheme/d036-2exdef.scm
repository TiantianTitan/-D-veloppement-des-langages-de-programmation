;;; $Id: d036-2exdef.scm 405 2006-09-13 17:21:53Z queinnec $
(comment "defined sur variable globale non definie")
(expected-result #t)
(expected-printing "")

(let ((r (defined x)))
  (set! x 11)
  (not r) )

;;; end of d036-2exdef.scm
