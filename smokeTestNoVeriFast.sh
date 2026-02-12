#!/bin/bash

echo "Checking that KeY (standard Dynamic Frames variant) is executable (should print help message of KeY) ..."
java -Dslf4j.internal.verbosity=ERROR -Dlogback.configurationFile=tools/disablelogging.xml -jar tools/key-2.12.4-dev-exe.jar "--help"
echo
echo "Checking that the Universe Encapsulation Type checker is executable (should print the help message of the Java compiler) ..."
tools/uet-checker/checkEnc.sh --help
echo
echo "Checking that KeY (Universe Encapsulation Types variant) is executable (should print help message of KeY) ..."
java -Dslf4j.internal.verbosity=ERROR -Dlogback.configurationFile=tools/disablelogging.xml -jar tools/key-2.12.4-UT-dev-exe.jar "--help"
echo
echo "Checking that citool is executable (should print help message of citool) ..."
java -cp tools/key-2.12.4-dev-exe.jar:tools/citool-1.7.0-SNAPSHOT-mini.jar io.github.wadoon.keycitool.CheckerKt --help
