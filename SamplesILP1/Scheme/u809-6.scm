;;; $Id: u809-6.scm 405 2006-09-13 17:21:53Z queinnec $
(comment "lecture d'un champ")
(expected-result 809)

(defclass Point Object
  (x y) )
(let ((point (new Point 809 22)))
  (oget point "x") )

;;; end of u809-6.scm
