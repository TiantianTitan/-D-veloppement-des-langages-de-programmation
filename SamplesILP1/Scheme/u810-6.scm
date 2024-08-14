;;; $Id: u810-6.scm 405 2006-09-13 17:21:53Z queinnec $
(comment "Point + ecriture/lecture champ")
(expected-result 810)

(defclass Point Object
  (x y) )
(let ((point (new Point 11 22)))
  (oset! point "x" 810)
  (oget point "x") )

;;; end of u810-6.scm
