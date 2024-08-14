;;; $Id$
;;; Test pour energ examen 2014jan
(comment "Interruption du corps par manque d'energie")

(try-catch-finally
 (confine 10
   (while #t
     (memoryConsume 4) )
   0 )
 (lambda (exc) (throw 4))
 #f )

;;; end of en-04.scm
