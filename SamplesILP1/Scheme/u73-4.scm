;;; $Id: u73-4.scm 405 2006-09-13 17:21:53Z queinnec $
(comment "detection de fonction recursive et integration grossissante")
(define (f1 x)
  (* 2 x) )
(define (f2 x y)
  (f1 (f1 x)) )
(define (f3 x)
  (f2 (f1 x) (f2 x x)) )

(define (fr1 x)
  (fr2 x) )
(define (fr2 x)
  (fr3 (fr3 x)) )
(define (fr3 x)
  (f3 (fr1 (fr3 x))) )

(f3 73)

;;; end of u73-4.scm
