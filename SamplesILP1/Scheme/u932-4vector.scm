;;; $Id$
(comment "longueur d'un vecteur a 932 cases" 932)
;;; La multiplication par 2 verifie que le resultat de vector-length
;;; est bien un nombre.
(let ((v (make-vector 3 (/ 932 2))))
  (* 2 (vector-length v)) )

;;; end of u932-4vector.scm
