;;; $Id: e122-4enum.scm 405 2006-09-13 17:21:53Z queinnec $
(comment "Une seule evaluation des expresssions de debut et fin")
(foreach i ( (begin (print 1) 1)
             .. 
             (begin (print 3) 3) )
   (print (* 10 i)) )

;;; end of e122-4enum.scm
