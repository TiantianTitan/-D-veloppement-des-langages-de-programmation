;;; $Id: e120-2enum.scm 405 2006-09-13 17:21:53Z queinnec $
(comment "Enumeration avec masquage interne de la variable d'iteration")
(foreach i (1 .. 3)
  (let ((i (* 2 i)))
    (print i) ) )

;;; end of e120-4enum.scm
