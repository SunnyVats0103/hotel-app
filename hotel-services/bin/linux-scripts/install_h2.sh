#!/bin/bash

# Set H2 version and credentials
H2_VERSION=2.2.224
H2_JAR="h2-${H2_VERSION}.jar"
H2_URL="https://repo1.maven.org/maven2/com/h2database/h2/${H2_VERSION}/${H2_JAR}"

# Download H2 jar if not present
if [ ! -f "$H2_JAR" ]; then
  echo "Downloading H2 Database..."
  curl -L -o "$H2_JAR" "$H2_URL"
fi

# Print download status
if [ -f "$H2_JAR" ]; then
  echo "H2 Database downloaded successfully: $H2_JAR"
else
  echo "Failed to download H2 Database."
  exit 1
fi

# Create a directory for placing the H2 jar file and move the jar file there
mkdir -p ~/h2
mv "$H2_JAR" ~/h2/
# Set permissions for the H2 jar file
chmod 644 "$H2_JAR"
# Create a directory for H2 data if it doesn't exist
mkdir -p ~/h2/h2-data
# Set permissions for the H2 data directory
chmod 700 ~/h2/h2-data
# Create a directory for H2 logs if it doesn't exist
mkdir -p ~/h2/h2-logs
# Set permissions for the H2 logs directory
chmod 700 ~/h2/h2-logs

# Set environment variables for H2
export H2_JAR=~/h2/$H2_JAR
export H2_DATA_DIR=~/h2/h2-data
export H2_LOGS_DIR=~/h2/h2-logs

# Print success message
echo "H2 Database installation completed successfully."