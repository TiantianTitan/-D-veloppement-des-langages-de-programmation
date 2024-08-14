;;; $Id$
;;; Test pour energ examen 2014jan
(comment "Sortie normale par interruption du corps")

(let ((r 0))
 (try-catch-finally
  (confine 10
   (throw 5)
   0 )
  (lambda (e) 
    (set! r e) )
  #f )
 r )

;;; end of en-05.scm
