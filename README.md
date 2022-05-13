# Maven Release Plugin

Given we have:
1. Our version model is <MAJOR.MINOR.PATCH>, where we will consider Major as an official release, Minor the bugfix fix and Patch any implementation or new feature that should be tested
2. Our production is `prod` branch, and it is used to create official and bugfix releases on packagecloud. Only `dev` can push to `prod`. When changes are committed and pushed, it should trigger circleci build:
   1. with tests
   2. increase snapshot version on `master` and deploy to packagecloud repository with appropriate version
      1. if git commit message contains `[bugfix]` (0.1.X-SNAPSHOT to 0.2 for release artifact and 0.2.0-SNAPSHOT on `master`)
      2. any other case will create major release (0.X.X-SNAPSHOT to 1.0 for release artifact and 1.0.0-SNAPSHOT on `master`)
4. Our git-default is branch `master`, and it is used to integrate new features. When changes are committed and pushed, it should trigger circleci build:
   1. with tests 
   2. increase snapshot version on `master` (0.0.1-SNAPSHOT to 0.0.2-SNAPSHOT)
5. For each feature we have an extra branch that contains word `feature` in its name (feature-Xxx). When changes are committed and pushed, it should trigger circleci build:
   1. with tests




- setting up release-plugin to deploy project snapshots and releases to Packagecloud
- setting up release-plugin and circleci to perform integration-test when circle brunch is updated
- setting up release-plugin and circleci to deploy new releases when master brunch is updated
- adding branch filter to circleci config
