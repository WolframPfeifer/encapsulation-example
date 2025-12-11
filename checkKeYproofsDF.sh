#!/bin/bash

# check the DF + KeY part
java -jar tools/key-2.12.4-dev-exe.jar "--auto" "key/example.Cell(example.Cell__init()).JML normal_behavior operation contract.0.proof"
java -jar tools/key-2.12.4-dev-exe.jar "--auto" "key/example.CellImpl(example.Cell__set(int)).JML normal_behavior operation contract.0.proof"
java -jar tools/key-2.12.4-dev-exe.jar "--auto" "key/example.CellImpl(example.Cell__value()).JML accessible clause.0.proof"
java -jar tools/key-2.12.4-dev-exe.jar "--auto" "key/example.CellImpl(example.Cell__value()).JML normal_behavior operation contract.0.proof"
java -jar tools/key-2.12.4-dev-exe.jar "--auto" "key/example.CellImpl(java.lang.Object___inv_()).JML accessible clause.0.proof"
