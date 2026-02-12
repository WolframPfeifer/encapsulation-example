#!/bin/bash

# check that both shipped KeY versions are executable
echo "Checking that KeY (standard Dynamic Frames variant) is executable (should print a list of all set properties) ..."
java -jar tools/key-2.12.4-dev-exe.jar "--show-properties"
echo
echo "Checking that the Universe Encapsulation Type checker is executable (should print the help message of the Java compiler) ..."
tools/uet-checker/checkEnc.sh --help
echo
echo "Checking that KeY (Universe Encapsulation Types variant) is executable (should print a list of the available flags and parameters) ..."
java -Dslf4j.internal.verbosity=ERROR -Dlogback.configurationFile=tools/disablelogging.xml -jar tools/key-2.12.4-UT-dev-exe.jar "--help"
echo
