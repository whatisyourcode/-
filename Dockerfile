# Use a Gradle image for building the application
FROM gradle:7.6.0-jdk17 as builder

# Set the working directory
WORKDIR /app

# Copy Gradle configuration files
COPY . /app
EXPOSE 8099
ENTRYPOINT [ "./gradlew", "bootRun" ]
