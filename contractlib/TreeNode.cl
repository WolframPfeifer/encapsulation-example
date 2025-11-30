(declare-abstractions
    ((TreeNode 0))
    (((TreeNode ((absVal set))))))

(define-contract TreeNode.init
    ((this (out TreeNode))
     (v (in Int)))
    ((true
     (= (absVal this) (set.singleton v)))))

(define-contract TreeNode.add
    ((this (inout TreeNode))
     (v (in Int)))
    ((true
     (forall ((x Int)) (or (set.in (x (old (absVal this)))) (= x v))))))

(define-contract TreeNode.contains
    ((this (in TreeNode))
     (result (out Bool)))
    ((true
     (= result (set.in (v (absVal this)))))))
