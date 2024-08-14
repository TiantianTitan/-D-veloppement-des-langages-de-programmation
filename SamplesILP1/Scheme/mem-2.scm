;;; $Id$

(define (alloue n)
  (let ((init (memoryGet)))
    (let ((end (+ init n)))
      (while (< (memoryGet) end)
         #t ) ) ) )
    
(let ((init (memoryGet))
      (n    1000) )
  ;(print init)(newline)
  (alloue n)
  (let ((end (memoryGet)))
    ;(print end)(newline)
    (> (+ init n) end) ) )

;;; end of mem-2.scm
