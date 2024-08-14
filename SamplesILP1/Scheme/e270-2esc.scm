;;; $Id$
(comment "dernier utilise en dehors de boucle")
(let ((i 0))
  (try-catch-finally
   (begin (last) i)
   (lambda (exc)
     (set! i 270) )
   3 )
  i )

;;; end of e270-2esc.scm
