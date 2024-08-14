;;; $Id$
(comment "modifier un vecteur..." 934)
(let ((v (make-vector 3 9)))
  (vector-set! v 0 9)
  (vector-set! v 1 3)
  (vector-set! v 2 4)
  (+ (vector-get v 0)
     (+ (vector-get v 1)
        (vector-get v 2) ) ) )

;;; end of u934-4vector.scm
