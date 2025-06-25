#!/bin/bash

RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m'

echo -e "${BLUE} Trust Wallet Appium Test Runner${NC}"
echo "=================================="

check_tool() {
    if ! command -v "$1" &>/dev/null; then
        echo -e "${RED} $1 not found in PATH${NC}"
        exit 1
    fi
}

check_tool appium
check_tool java
check_tool mvn

if command -v allure &>/dev/null; then
    ALLURE_CLI=true
else
    ALLURE_CLI=false
fi

check_android_sdk() {
    if [ -z "$ANDROID_HOME" ] && [ -z "$ANDROID_SDK_ROOT" ]; then
        echo -e "${YELLOW}  ANDROID_HOME/ANDROID_SDK_ROOT not set${NC}"
        echo "Android SDK path not configured, but continuing..."
    else
        echo -e "${GREEN} Android SDK found: ${ANDROID_HOME:-$ANDROID_SDK_ROOT}${NC}"
    fi
}

check_adb() {
    if ! command -v adb &> /dev/null; then
        echo -e "${YELLOW} ADB not found in PATH${NC}"
        echo "Make sure Android SDK platform-tools is in your PATH"
    else
        echo -e "${GREEN} ADB found: $(adb version | head -n 1)${NC}"
    fi
}

appium server -p 4725 -a 127.0.0.1 -pa /wd/hub &
APPIUM_PID=$!
sleep 5
if curl -s http://127.0.0.1:4725/wd/hub/status | grep -q '"ready":true'; then
    echo -e "${GREEN} Appium server started (PID $APPIUM_PID)${NC}"
else
    echo -e "${RED} Appium server failed to start${NC}"
    kill $APPIUM_PID 2>/dev/null
    exit 1
fi

cleanup() {
    kill $APPIUM_PID 2>/dev/null
}
trap cleanup EXIT

SERVE=false
NO_REPORT=false
while [[ $# -gt 0 ]]; do
    case $1 in
        --serve) SERVE=true; shift;;
        --no-report) NO_REPORT=true; shift;;
        --help)
            echo "Usage: $0 [--serve] [--no-report]"
            exit 0;;
        *) echo -e "${RED} Unknown option: $1${NC}"; exit 1;;
    esac
done

mvn clean test
TEST_EXIT=$?

if [ "$NO_REPORT" = false ]; then
    if [ -d target/allure-results ] && [ "$(ls -A target/allure-results)" ]; then
        mvn allure:report
        echo -e "${GREEN} Allure report generated: target/site/allure-maven-plugin/index.html${NC}"
        if [ "$SERVE" = true ]; then
            if $ALLURE_CLI; then
                allure serve target/allure-results
            else
                mvn allure:serve
            fi
        fi
    else
        echo -e "${YELLOW} No Allure results found, skipping report${NC}"
    fi
else
    echo -e "${YELLOW} Skipping Allure report generation (--no-report)${NC}"
fi

exit $TEST_EXIT 