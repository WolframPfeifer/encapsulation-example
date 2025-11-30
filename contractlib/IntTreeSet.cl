(declare-abstractions
    ((IntTreeSet 0))
    (((IntTreeSet ((absVal set))))))

(define-contract IntTreeSet.init
    ((this (out IntTreeSet))
     (v (in Int)))
    ((true
     (= (absVal this) set.empty))))

(define-contract IntTreeSet.add
    ((this (inout IntTreeSet)) ; only in?
     (v (in Int)))
    ((true
     (forall ((x Int)) (or (set.in (x (old (absVal this)))) (= x v))))))

(define-contract IntTreeSet.contains
    ((this (inout IntTreeSet)) ; only in?
     (result (out Bool)))
    ((true
     (= result (set.in (v (absVal this)))))))
