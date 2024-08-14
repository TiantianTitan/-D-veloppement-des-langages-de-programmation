;;; $Id: d015-2exdef.scm 405 2006-09-13 17:21:53Z queinnec $
(comment "existence d'une variable globale inexistante")
(expected-result #t)
(expected-printing "")

(not (exists x))
    
;;; end of d015-2exdef.scm
