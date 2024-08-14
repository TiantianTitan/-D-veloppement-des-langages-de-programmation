;;; $Id: u27-2.scm 405 2006-09-13 17:21:53Z queinnec $
(comment "bloc local avec 2 variables et masquage")
(let ((x 1)
      (y 2) )
  (let ((y 3))
    y ) )

;;; end of u27-2.scm
