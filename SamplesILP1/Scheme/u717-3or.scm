;;; $Id$
;;; Interruption d'evaluation avant effet secondaire
(or (print "OK") ; returns #f
    #t
    (print "NotOK") )

;;; end of u717-3or.scm
