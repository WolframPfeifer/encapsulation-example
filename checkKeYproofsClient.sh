#!/bin/bash

# check the DF + KeY part
java -jar tools/key-2.12.4-dev-exe.jar "--auto" "client+key-interfaces/example.Client(example.Client__m()).JML normal_behavior operation contract.0.proof"
