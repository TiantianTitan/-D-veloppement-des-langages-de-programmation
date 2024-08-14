;;; $Id: u805-6.scm 405 2006-09-13 17:21:53Z queinnec $
(comment "Creation d'une classe sans champ ni methode, allocation")
(expected-result 805)

(defclass Point Object 
  () )
(new Point)
805

;;; end of u805-6.scm
