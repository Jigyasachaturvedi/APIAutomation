#!/bin/bash
set -e

echo "Running test suite..."
mvn test -DsuiteXmlFile=testng.xml

echo "Generating single HTML report..."
allure generate ${TEST_RESULTS_PATH} --clean --single-file -o ${REPORT_PATH}

echo "Report generated successfully at ${REPORT_PATH}/index.html"