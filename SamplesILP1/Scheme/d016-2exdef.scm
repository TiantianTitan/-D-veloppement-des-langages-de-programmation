;;; $Id: d016-2exdef.scm 405 2006-09-13 17:21:53Z queinnec $
(comment "existence d'une variable locale")
(expected-result #t)
(expected-printing "")

(let ((x 1))
  (exists x) )

;;; end of d016-2exdef.scm
