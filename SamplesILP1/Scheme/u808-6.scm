;;; $Id: u808-6.scm 405 2006-09-13 17:21:53Z queinnec $
(comment "Creation d'une classe avec 2 champs, sans methode + allocation")
(expected-result 808)

(defclass Point Object 
  (x y) )
(new Point 11 22)
808

;;; end of 808-6.scm
