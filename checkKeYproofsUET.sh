#!/bin/bash

# check the DF + KeY part
#java -jar tools/key-2.12.4-UT-dev-exe.jar "--auto" "universe/example.IntTreeSet(example.IntTreeSet__init()).JML normal_behavior operation contract.0.proof"
#java -jar tools/key-2.12.4-UT-dev-exe.jar "--auto" "universe/example.IntTreeSetImpl(example.IntTreeSet__add(int)).JML normal_behavior operation contract.0.proof"
#java -jar tools/key-2.12.4-UT-dev-exe.jar "--auto" "universe/example.IntTreeSetImpl(example.IntTreeSet__contains(int)).JML accessible clause.0.proof"
#java -jar tools/key-2.12.4-UT-dev-exe.jar "--auto" "universe/example.IntTreeSetImpl(example.IntTreeSet__contains(int)).JML normal_behavior operation contract.0.proof"
#java -jar tools/key-2.12.4-UT-dev-exe.jar "--auto" "universe/example.IntTreeSetImpl(example.IntTreeSetImpl__IntTreeSetImpl()).JML normal_behavior operation contract.0.proof"
#java -jar tools/key-2.12.4-UT-dev-exe.jar "--auto" "universe/example.IntTreeSetImpl(java.lang.Object___inv_()).JML accessible clause.0.proof"
#java -jar tools/key-2.12.4-UT-dev-exe.jar "--auto" "universe/example.TreeNode(example.TreeNode__add(int)).JML normal_behavior operation contract.0.proof"
#java -jar tools/key-2.12.4-UT-dev-exe.jar "--auto" "universe/example.TreeNode(example.TreeNode__contains(int)).JML accessible clause.0.proof"
#java -jar tools/key-2.12.4-UT-dev-exe.jar "--auto" "universe/example.TreeNode(example.TreeNode__contains(int)).JML normal_behavior operation contract.0.proof"
#java -jar tools/key-2.12.4-UT-dev-exe.jar "--auto" "universe/example.TreeNode(example.TreeNode__TreeNode(int)).JML normal_behavior operation contract.0.proof"
#java -jar tools/key-2.12.4-UT-dev-exe.jar "--auto" "universe/example.TreeNode(java.lang.Object___inv_()).JML accessible clause.0.proof"

java -Dslf4j.internal.verbosity=ERROR -Dlogback.configurationFile=tools/disablelogging.xml -cp "tools/key-2.12.4-UT-dev-exe.jar:tools/key-citool-1.7.0-SNAPSHOT-mini.jar" io.github.wadoon.keycitool.CheckerKt -v --proof-path universe universe
