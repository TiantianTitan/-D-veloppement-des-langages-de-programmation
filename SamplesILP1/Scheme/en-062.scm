;;; $Id$
;;; Test pour energ examen 2014jan
(comment "Portee et confine")

;(let ((rr 0))
;  (set! rr 1)
;  rr )

(let ((r 0))
  (confine 100
     (set! r 1) )
  r )

;;; end of en-062.scm
