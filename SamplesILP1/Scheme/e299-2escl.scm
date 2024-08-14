;;; $Id: e299-2escl.scm 405 2006-09-13 17:21:53Z queinnec $
(comment "exemple de l'examen")
(let ((i 1))
  (whileWithBreak "A" (< i 10)
     (set! i (+ i 1))
     (let ((j 0))
       (whileWithBreak "B" (< j 10)
          (set! j (+ j 1))
          (if (= (modulo j 2) 0)
              (next "B") )
          (print j)
          (if (= j (* i i))
              (last "A") ) ) ) ) )

;;; end of e299-2escl.scm
