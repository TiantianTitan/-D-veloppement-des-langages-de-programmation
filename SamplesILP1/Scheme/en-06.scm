;;; $Id$
;;; Test pour energ examen 2014jan
(comment "Deux confinements")

(confine 100
  (print 1)
  (confine 200
    (print 2) )
  (print 3)
  6 )

;;; end of en-06.scm
