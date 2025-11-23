#!/bin/sh
echo "Running Checkstyle..."

# Run Maven Checkstyle
mvn checkstyle:check
RESULT=$?

if [ $RESULT -ne 0 ]; then
  echo "Code style violations detected. Please fix them before committing."
  exit 1
fi

echo "Style check passed."
exit 0
