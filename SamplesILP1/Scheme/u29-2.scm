;;; $Id: u29-2.scm 405 2006-09-13 17:21:53Z queinnec $
(comment "bloc local avec 2 variables (portee initialisation)")
(let ((x 11)
      (y 22) )
  (let ((x (+ x y))
        (y (* x y)) )
    (* x y) ) )
    
;;; end of u29-2.scm
