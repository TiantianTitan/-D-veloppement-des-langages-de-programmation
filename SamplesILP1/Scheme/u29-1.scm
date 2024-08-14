;;; $Id: u29-1.scm 405 2006-09-13 17:21:53Z queinnec $
(comment "bloc unaire (portee des initialisations)" 36)
(let ((x 3))
  (let ((x (+ x x)))
    (* x x) ) )

;;; end of u29-1.scm
