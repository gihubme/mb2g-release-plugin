#!/bin/bash

git checkout main
mvn --batch-mode build-helper:parse-version versions:set -DnewVersion='${parsedVersion.majorVersion}.${parsedVersion.nextMinorVersion}.0-SNAPSHOT' versions:commit
mvn deploy -s ../.circleci/settings.xml
