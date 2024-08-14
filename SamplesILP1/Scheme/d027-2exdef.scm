;;; $Id: d027-2exdef.scm 405 2006-09-13 17:21:53Z queinnec $
(comment "exists sur une variable globale lue apres")
(expected-result #t)
(expected-printing "")

(let ((bar (exists foo)))
  (if #f foo)
  bar )

;;; end of d027-2exdef.scm
