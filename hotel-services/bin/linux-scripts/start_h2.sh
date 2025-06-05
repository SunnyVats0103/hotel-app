#!/bin/bash

# Define the path to the H2 jar file
DB_USER="root"
DB_PASS="P@ssword"

# Start H2 TCP server (local only) with data directory and logging
H2_JAR=~/h2/h2-2.2.224.jar
if [ ! -f "$H2_JAR" ]; then
  echo "H2 jar file not found at $H2_JAR. Please ensure it is installed."
  exit 1
fi

# Use data and logs directories from location ~/h2/h2-data and ~/h2/h2-logs
H2_DATA_DIR=~/h2/h2-data
H2_LOGS_DIR=~/h2/h2-logs

# Start the H2 server with specified data and logs directories already created
mkdir -p "$H2_DATA_DIR"
mkdir -p "$H2_LOGS_DIR"
chmod 700 "$H2_DATA_DIR"
chmod 700 "$H2_LOGS_DIR"
export H2_OPTIONS="-ifNotExists -tcpAllowOthers -tcpPort 9092 -baseDir $H2_DATA_DIR -logDir $H2_LOGS_DIR"
# Start the H2 TCP server
echo "Starting H2 Database server with data directory: $H2_DATA_DIR and logs directory: $H2_LOGS_DIR"
nohup java -cp "$H2_JAR" org.h2.tools.Server -tcp "$H2_OPTIONS" > "$H2_LOGS_DIR/h2.log" 2>&1 &

# Wait for the server to start
sleep 5
if ! pgrep -f "org.h2.tools.Server" > /dev/null; then
  echo "Failed to start H2 server. Check logs at $H2_LOGS_DIR/h2.log"
  exit 1
fi

# Check if the server is running
if ! netstat -tuln | grep -q ':9092'; then
  echo "H2 server is not running on port 9092."
  exit 1
fi

# Print connection information
echo "H2 Database server is running."
echo "H2 server started. Connect using:"
echo "JDBC URL: jdbc:h2:tcp://localhost:9092/~/test;USER=$DB_USER;PASSWORD=$DB_PASS"