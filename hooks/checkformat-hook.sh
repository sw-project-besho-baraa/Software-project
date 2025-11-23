#!/bin/sh
echo "Running Java format check with Spotless..."

# Automatically apply fixes
mvn spotless:apply
RESULT=$?

if [ $RESULT -ne 0 ]; then
  echo "Error: Spotless failed to apply formatting."
  exit 1
fi

echo "Formatting applied successfully."
exit 0
