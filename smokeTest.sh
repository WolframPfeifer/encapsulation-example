#!/bin/bash

# check that VeriFast is executable
echo "Checking that VeriFast is executable (should print a list of available flags and parameters) ..."
# should print a list of the available flags and parameters of VeriFast
./tools/vf/verifast-26.01-linux-amd64/bin/verifast --help
echo

# check that other tools are executable
./smokeTestNoVeriFast.sh
