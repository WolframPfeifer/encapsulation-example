#!/bin/bash

# check the DF + KeY part
#java -jar tools/key-2.12.4-dev-exe.jar "--auto" "client+key-interfaces/example.Client(example.Client__m()).JML normal_behavior operation contract.0.proof"

java -Dslf4j.internal.verbosity=ERROR -Dlogback.configurationFile=tools/disablelogging.xml -cp "tools/key-2.12.4-dev-exe.jar:tools/key-citool-1.7.0-SNAPSHOT-mini.jar" io.github.wadoon.keycitool.CheckerKt -v --proof-path client+key-interfaces client+key-interfaces
