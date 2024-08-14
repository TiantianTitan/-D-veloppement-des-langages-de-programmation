;;; $Id$
;;; Test pour energ examen 2014jan
(comment "Deux confinements, l'externe manque d'energie")

(let ((r 0))
  (try-catch-finally
   (confine 100
     (set! r 1)
     (try-catch-finally
      (confine 200
         (set! r 2)
         (memoryConsume 100)
         (set! r 3) )
      (lambda (e)
        (set! r -4) )
      #f )
     (memoryConsume 300) )
   (lambda (e)
     (set! r 5) )
   #f )
  r ) ; 5


;;; end of en-08.scm
