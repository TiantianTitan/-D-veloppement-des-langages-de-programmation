;;; $Id$
;;; Test pour energ examen 2014jan
(comment "La fonction memoryConsume fonctionne")

(confine (+ 500 200)
   (memoryConsume 30)
   (>= (memoryGet) 30) )

;;; end of en-03.scm
