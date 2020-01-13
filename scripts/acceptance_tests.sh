#! /usr/bin/env bash

set -Eeou pipefail

function finish {
  echo "Killing server process!"
  kill "$SERVER_PID"
}
trap finish EXIT

./gradlew jar
cd http_server_spec
bundle install
java -jar "../build/libs/$(ls ../build/libs)" &
SERVER_PID=$!
bundle exec spinach