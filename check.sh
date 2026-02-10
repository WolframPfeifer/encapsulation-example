#!/bin/bash

#./checkKeYproofsClient.sh
#./checkKeYproofsDF.sh
#./checkUET.sh
#./checkKeYproofsUET.sh
#./checkVeriFast.sh

KEY_JAR=tools/key-2.12.4-dev-exe.jar
KEY_UET_JAR="tools/key-2.12.4-UT-dev-exe.jar"
CI_TOOL="tools/key-citool-1.7.0-SNAPSHOT-mini.jar"

checkCommand="java -Dslf4j.internal.verbosity=ERROR -Dlogback.configurationFile=tools/disablelogging.xml"

echo "Checking the Client part ..."
$checkCommand -cp "$KEY_JAR:$CI_TOOL" io.github.wadoon.keycitool.CheckerKt -v --proof-path client+key-interfaces client+key-interfaces

echo
echo "Checking the KeY (Dynamic Frames) part ..."
$checkCommand -cp "$KEY_JAR:$CI_TOOL" io.github.wadoon.keycitool.CheckerKt -v --proof-path key key

echo
echo "Running the Universe Encapsulation Type checker ..."
SCRIPT_FILE="$(realpath "$0")"
MYDIR="$(dirname "$SCRIPT_FILE")"

ENCCHECKERDIR="tools"
cd $ENCCHECKERDIR
./checkEnc.sh $MYDIR/universe/*.java
cd $MYDIR

echo
echo "Checking the KeY + Universe Types part ..."
$checkCommand -cp "$KEY_UET_JAR:$CI_TOOL" io.github.wadoon.keycitool.CheckerKt -v --proof-path universe universe

echo
echo "Checking the VeriFast part ... "
./tools/vf/verifast -c -allow_dead_code -shared verifast/sources.jarsrc
