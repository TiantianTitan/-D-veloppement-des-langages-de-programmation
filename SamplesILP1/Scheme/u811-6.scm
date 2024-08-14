;;; $Id: u811-6.scm 405 2006-09-13 17:21:53Z queinnec $
(comment "Point + ecriture/lecture champ")
(expected-result 811)

(defclass Point Object
  (x y) )
(let ((point (new Point 11 22)))
  (oset! point "y" 811)
  (oget point "y") )

;;; end of u811-6.scm
