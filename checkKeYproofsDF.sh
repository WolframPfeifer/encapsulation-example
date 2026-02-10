#!/bin/bash

# check the DF + KeY part
#java -jar tools/key-2.12.4-dev-exe.jar "--auto" "key/example.Cell(example.Cell__init()).JML normal_behavior operation contract.0.proof"
#java -jar tools/key-2.12.4-dev-exe.jar "--auto" "key/example.CellImpl(example.Cell__set(int)).JML normal_behavior operation contract.0.proof"
#java -jar tools/key-2.12.4-dev-exe.jar "--auto" "key/example.CellImpl(example.Cell__value()).JML accessible clause.0.proof"
#java -jar tools/key-2.12.4-dev-exe.jar "--auto" "key/example.CellImpl(example.Cell__value()).JML normal_behavior operation contract.0.proof"
#java -jar tools/key-2.12.4-dev-exe.jar "--auto" "key/example.CellImpl(java.lang.Object___inv_()).JML accessible clause.0.proof"

# slf4j.internal.verbosity: suppress "multiple SLF4J providers" warning
# logback.configurationFile: disable internal logging of KeY, we use citool for output messages
java -Dslf4j.internal.verbosity=ERROR -Dlogback.configurationFile=tools/disablelogging.xml -cp "tools/key-2.12.4-dev-exe.jar:tools/key-citool-1.7.0-SNAPSHOT-mini.jar" io.github.wadoon.keycitool.CheckerKt -v --proof-path key key
