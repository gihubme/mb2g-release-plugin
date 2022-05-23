### Gitworkflow

- Our version model is <MAJOR.MINOR.PATCH>, where we will consider Major as an official release, Minor the bugfix fix
  and Patch any implementation or new feature that should be tested

- Our git-default is branch `master`, and it is used to integrate new features. When changes are committed and pushed,
  it should trigger circleci build:

    1. run unit and integration tests
    2. increase snapshot version on `master` (0.0.1-SNAPSHOT to 0.0.2-SNAPSHOT)

- For each feature we have an extra branch that contains word `feature` in its name (feature-Xxx). Before last changes
  are committed and pushed, the brunch should be rebased on m`master` and new tests should be run locally. When changes
  are committed and pushed, it should trigger circleci build:

    1. run unit and integration tests

- To create official and bugfix releases on packagecloud

    1. run maven verify and deploy either for minor or major version change

###### NOT USED ANYMORE

- Our production is `prod` branch, and it is used to create official and bugfix releases on packagecloud. Only `master`
  can push to `prod`. When changes are committed and pushed, it should trigger circleci build:

    1. run unit and integration tests
    2. increase snapshot version on `master`
    3. deploy to packagecloud repository with appropriate version
    4. if git commit message contains `[major]` (0.X.X-SNAPSHOT to 1.0 for release artifact and 1.0.0-SNAPSHOT
       on `master`)
    5. any other case will create major release (0.1.X-SNAPSHOT to 0.2 for release artifact and 0.2.0-SNAPSHOT
       on `master`)

### Maven Release Plugin

- [Build-helper-maven-plugin](https://www.mojohaus.org/build-helper-maven-plugin/index.html) to parse the version into
  different properties.

- Setting up deployment management maven section for [packagecloud](https://packagecloud.io/nikakar) repositories and
  maven-packagecloud-wagon extension. Run `mvn deploy` to see it in action.

- Setting up [release-plugin](https://maven.apache.org/maven-release/maven-release-plugin/index.html), `project.scm.id`
  property and SCM maven section to deploy project snapshots and releases to Packagecloud repositories.
  Run `mvn clean release:prepare` with `mvn release:rollback` or with `mvn release:perform`. NOTE: requires
  Github-personal-api-token in ~/.m2/settings.xml for the relevant github account server configuration (server id
  as `project.scm.id`).

- Setting up circleci config to perform integration and unit tests when `feature-X`, `prod` or `main` branches are
  updated.

- Setting up circleci config to increase snapshot version when `main` branch is updated. NOTE: requires
  github-deployment-key set up in circleci project settings.

###### NOT USED ANYMORE

Setting up circleci config to deploy major and minor releases when `prod` branch is updated.  
NOTE: requires settings.xml in .circleci folder for the wagon-extension in order to be able to deploy to packagecloud.

### [Running Tests with maven](https://stackoverflow.com/questions/1399240/how-do-i-get-my-maven-integration-tests-to-run)

Run below to execute **only Unit Tests** `mvn clean test`

You can execute below command to run the **both unit and integration** `mvn clean verify`

In order to run **only Integration Tests**, follow `mvn failsafe:integration-test`

#### With maven install

In order to **skip unit tests** during mvn install, follow `mvn clean install -DskipUnitTests`

In order to **skip integration tests** during mvn install, follow `mvn clean install -DskipIntegrationTests`

In order to **skip all tests** during mvn install, follow `mvn clean install -DskipTests`

a1
