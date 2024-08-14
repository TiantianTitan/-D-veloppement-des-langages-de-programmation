;;; $Id$
(comment "benchmark objet")
(expected-result 8999)
(expected-printing 1000)

(defclass Point Object
  (x)
  (define (moveX dx)
    (oset! (self) "x" (+ (oget (self) "x") dx))
    (self) ) )

(defclass Point2D Point
  (y)
  (define (moveY dy)
    (oset! (self) "y" (+ (oget (self) "y") dy))
    (self) )
  (define (print)
    (print "print@Point2D")
    (super) ) )

(defclass Point3D Point2D
  (z)
  (define (print)
    (print "print@Point3D")
    (super) ) )

(defclass Point2Dcolored Point2D
  (color)
  (define (print)
    (print "print@Point2Dcolored")
    (super) ) )

(defclass Point3Dcolored Point2Dcolored
  (zz)
  (define (print)
    (print "print@Point3Dcolored")
    (super) ) )

(define (rand max)
  ;; N'importe quoi!
  (set! seed (modulo (* 16807 seed) 2147483647))
  (modulo seed max) )

;;; 1000 invocations
(let ((k 1000)
      (pt 0) )
  (set! seed 1)
  (print k)
  (while (> k 0)
     (set! k (- k 1))
     (let ((random (rand 4)))
       ;; allouer un point quelconque
       (if (= random 0)
           (set! pt (new Point2D 1 2))
           (if (= random 1)
               (set! pt (new Point3D 1 2 3))
               (if (= random 2)
                   (set! pt (new Point2Dcolored 1 2 "blue"))
                   (set! pt (new Point3Dcolored 1 2 3 "red")) ) ) ) )
     (let ((random (rand 2)))
       ;; invoquer une methode quelconque
       (if (= random 0)
           (send "moveX" pt 11)
           (send "moveY" pt 22) ) ) )
  8999 )

;;; end of u8999-6.scm
