#!/bin/bash

# check that VeriFast is executable
echo "Check that VeriFast is executable (should print a list of available flags and parameters) ..."
# should print a list of the available flags and parameters of VeriFast
./tools/vf/verifast --help

# check that both shipped KeY versions are executable
echo
echo "Check that KeY (standard Dynamic Frames variant) is executable (should print a list of all set properties) ..."
java -jar tools/key-2.12.4-dev-exe.jar "--show-properties"
echo
echo "Check that KeY (standard Dynamic Frames variant) is executable (should print a list of the available flags and parameters) ..."
java -Dslf4j.internal.verbosity=ERROR -Dlogback.configurationFile=tools/disablelogging.xml -jar tools/key-2.12.4-UT-dev-exe.jar "--help"
