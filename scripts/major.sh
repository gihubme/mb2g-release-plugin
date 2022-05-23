#!/bin/bash

git checkout main
mvn --batch-mode build-helper:parse-version versions:set -DnewVersion='${parsedVersion.nextMajorVersion}.0.0-SNAPSHOT' versions:commit
mvn --batch-mode clean release:prepare release:perform
