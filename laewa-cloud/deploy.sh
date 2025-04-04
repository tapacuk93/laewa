set -e

./gradlew clean shadowJar

aws lambda update-function-code \
  --function-name laewa \
  --zip-file fileb://build/libs/laewa-cloud-1.0-all.jar \
  --profile personal \
  --no-cli-pager