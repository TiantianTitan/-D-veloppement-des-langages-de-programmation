;;; $Id: d025-2exdef.scm 405 2006-09-13 17:21:53Z queinnec $
(comment "exists sur une variable globale ecrite apres")
(expected-result #t)
(expected-printing "")

(let ((bar (exists foo)))
  (set! foo 1)
  bar )

;;; end of d025-2exdef.scm
