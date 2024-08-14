;;; $Id$
(expected-result 5412)
(expected-printing "")

(define (moinsun x)
  (if (> x 0)
      (finalValue (- x 1)) ) )

(moinsun (moinsun 5414))

;;; end of u5412-2retval.scm
