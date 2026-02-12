#!/bin/bash

KEY_JAR=tools/key-2.12.4-dev-exe.jar
KEY_UET_JAR=tools/key-2.12.4-UT-dev-exe.jar
CI_TOOL=tools/citool-1.7.0-SNAPSHOT-mini.jar

# slf4j.internal.verbosity: suppress "multiple SLF4J providers" warning
# logback.configurationFile: disable internal logging of KeY, we use citool for output messages
JAVA_CMD="java -Dslf4j.internal.verbosity=ERROR -Dlogback.configurationFile=tools/disablelogging.xml"

echo "Checking the Client part ..."
$JAVA_CMD -cp "$KEY_JAR:$CI_TOOL" io.github.wadoon.keycitool.CheckerKt -v --proof-path client+key-interfaces client+key-interfaces

echo
echo "Checking the KeY (Dynamic Frames) part ..."
$JAVA_CMD -cp "$KEY_JAR:$CI_TOOL" io.github.wadoon.keycitool.CheckerKt -v --proof-path key key

echo
echo "Running the Universe Encapsulation Type checker ..."
tools/checkEnc.sh universe/*.java

echo
echo "Checking the KeY + Universe Types part ..."
$JAVA_CMD -cp "$KEY_UET_JAR:$CI_TOOL" io.github.wadoon.keycitool.CheckerKt -v --proof-path universe universe
