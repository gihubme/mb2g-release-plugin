#!/bin/bash

#SCRIPT_DIR="$( cd -- "$( dirname -- "${BASH_SOURCE[0]:-$0}"; )" &> /dev/null && pwd 2> /dev/null; )";
#echo $SCRIPT_DIR
git checkout main
mvn --batch-mode build-helper:parse-version versions:set -DnewVersion='${parsedVersion.majorVersion}.${parsedVersion.nextMinorVersion}.0-SNAPSHOT' versions:commit
#mvn deploy -s $SCRIPT_DIR/../.circleci/settings.xml
mvn mvn --batch-mode clean release:prepare release:perform
