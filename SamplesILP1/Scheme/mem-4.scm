;;; $Id$

(define (alloue n)
  (let ((init (memoryGet)))
    (let ((end (+ init n)))
      (while (< (memoryGet) end)
         #t ) ) ) )
    
(let ((n    1000) )
  ;(print init)(newline)
  (alloue n)
  (memoryReset)
  (= 0 (memoryGet)) 
  (> 0 (memoryGet)) )

;;; end of mem-4.scm
