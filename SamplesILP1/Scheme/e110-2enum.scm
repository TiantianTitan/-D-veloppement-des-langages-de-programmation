;;; $Id: e110-2enum.scm 405 2006-09-13 17:21:53Z queinnec $
(comment "Enumeration avec masquage externe de la variable d'iteration")
(let ((i 11))
  (foreach i (1 .. 3)
    (print i) )
  (print i) )

;;; end of e110-4enum.scm
