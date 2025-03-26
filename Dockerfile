# Use Maven with OpenJDK 11
FROM maven:3.8.4-openjdk-11-slim

# Install necessary utilities
RUN apt-get update && \
    apt-get install -y wget vim unzip && \
    rm -rf /var/lib/apt/lists/*


# Set environment variables
ENV ALLURE_VERSION=2.32.0
ENV REPORT_PATH=/app/target/allure-report
ENV TEST_RESULTS_PATH=/app/allure-results

# Install Allure commandline
RUN wget https://repo.maven.apache.org/maven2/io/qameta/allure/allure-commandline/${ALLURE_VERSION}/allure-commandline-${ALLURE_VERSION}.zip && \
    unzip allure-commandline-${ALLURE_VERSION}.zip -d /opt/ && \
    ln -s /opt/allure-${ALLURE_VERSION}/bin/allure /usr/bin/allure && \
    rm allure-commandline-${ALLURE_VERSION}.zip

# Set the working directory in the container
WORKDIR /app

# Copy only pom.xml first to leverage Docker cache for dependencies
COPY pom.xml .

# Pre-download Maven dependencies
RUN mvn dependency:resolve

# Copy the rest of the project
COPY . /app/

# Set the entrypoint to our custom script
ENTRYPOINT ["/bin/bash","/app/entrypoint.sh"]
