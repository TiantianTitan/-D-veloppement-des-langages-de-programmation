;;; $Id: u819-6.scm 405 2006-09-13 17:21:53Z queinnec $
(comment "invocation methode inexistante")
(expected-result 819)

(defclass Point Object
  (x y)
  (define (longueur)
    (+ (oget (self) "x") (oget (self) "y")) )
  (define (m1 z t)
    (+ (* z (oget (self) "x"))
       (* t (oget (self) "y")) ) ) )

;;; La compilation engendre un objet ILP_object_nexistePas_method ce
;;; qui provoque une erreur statique dans gcc. On truande pour ce test
;;; en forcant la creation (par ailleurs) de cet objet.
(defclass Truc Object
  ()
  (define (nexistePas x) x)
)

(let ((point (new Point 2 3)))
  (try-catch-finally
   (send "nexistePas" point 23)
   (lambda (e) #t)
   #f )
  819 )

;;; end of u819-6.scm
