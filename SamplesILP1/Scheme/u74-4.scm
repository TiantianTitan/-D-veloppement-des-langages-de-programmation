;;; $Id$
(comment "detection de fonction recursive et integration utile grossissante")
(define (f1 x)
  (if (< x 74) 
      (* 2 x)
      x ) )
(define (f2 x y)
  (f1 (f1 x)) )
(define (f3 x)
  (f2 (f1 x) (f2 x x)) )

(define (fr1 x)
  (fr2 x) )
(define (fr2 x)
  (fr3 (fr3 x)) )
(define (fr3 x)
  (f3 (f1 (f3 x))) )

(fr3 74)

;;; end of u74-4.scm
