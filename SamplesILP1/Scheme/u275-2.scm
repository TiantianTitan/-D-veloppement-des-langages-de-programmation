;;; $Id: u275-2.scm 405 2006-09-13 17:21:53Z queinnec $
(comment "bloc local avec 2 variables et double masquage")
(let ((x 1)
      (y 2) )
  (let ((y 3)
        (x 4) )
    (+ x y) ) )

;;; end of u275-2.scm
