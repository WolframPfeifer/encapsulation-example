#!/bin/bash

SCRIPT_FILE="$(realpath "$0")"
MYDIR="$(dirname "$SCRIPT_FILE")"

ENCCHECKERDIR="tools"
cd $ENCCHECKERDIR
#pwd
./checkEnc.sh $MYDIR/universe/*.java
cd $MYDIR
#pwd
