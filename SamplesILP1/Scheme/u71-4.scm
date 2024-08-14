;;; $Id: u71-4.scm 405 2006-09-13 17:21:53Z queinnec $
(comment "bloc local en expression")
(if (let ((x #t))
      x )
    1
    2 )

;;; end of u71-4.scm
