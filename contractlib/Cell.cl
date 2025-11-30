(declare-abstractions
    ((Cell 0))
    (((Cell ((absVal Int))))))

(define-contract Cell.init
    ((this (out Cell))
     (id (in Int))
     (v (in Int)))
    ((true
     (= (absVal this) v))))

(define-contract Cell.value
    ((this (inout Cell)) ; only in?
     (result (out Int)))
    ((true
     (= result (absVal this)))))

(define-contract Cell.compareTo
  ((this (inout Cell)) ; only in?
   (other (in Cell))
   (result (out Bool)))
  ((true
   (= result (- (absVal this) (absVal other))))))
