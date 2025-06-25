#!/bin/bash

RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
PURPLE='\033[0;35m'
CYAN='\033[0;36m'
NC='\033[0m'

echo -e "${BLUE}=== Environment Validation ===${NC}"

if command -v java &> /dev/null; then
    echo -e "${GREEN}✓ Java found${NC}"
else
    echo -e "${RED}✗ Java not found${NC}"
    exit 1
fi

if command -v mvn &> /dev/null; then
    echo -e "${GREEN}✓ Maven found${NC}"
else
    echo -e "${RED}✗ Maven not found${NC}"
    exit 1
fi

if command -v appium &> /dev/null; then
    echo -e "${GREEN}✓ Appium found${NC}"
else
    echo -e "${RED}✗ Appium not found${NC}"
    exit 1
fi

if command -v adb &> /dev/null; then
    echo -e "${GREEN}✓ ADB found${NC}"
else
    echo -e "${RED}✗ ADB not found${NC}"
    exit 1
fi

if command -v allure &> /dev/null; then
    echo -e "${GREEN}✓ Allure found${NC}"
else
    echo -e "${RED}✗ Allure not found${NC}"
    exit 1
fi

echo -e "${CYAN}=== Configuration Files ===${NC}"

if [ -f "pom.xml" ]; then
    echo -e "${GREEN}✓ Maven POM: pom.xml${NC}"
else
    echo -e "${RED}✗ Maven POM: pom.xml${NC}"
    exit 1
fi

if [ -f "src/testng.xml" ]; then
    echo -e "${GREEN}✓ TestNG XML: src/testng.xml${NC}"
else
    echo -e "${RED}✗ TestNG XML: src/testng.xml${NC}"
    exit 1
fi

if [ -f "src/main/resources/device-config.yaml" ]; then
    echo -e "${GREEN}✓ Device Config: src/main/resources/device-config.yaml${NC}"
else
    echo -e "${RED}✗ Device Config: src/main/resources/device-config.yaml${NC}"
    exit 1
fi

if [ -f "src/apps/android/androidAPP.apk" ]; then
    echo -e "${GREEN}✓ Android APK: src/apps/android/androidAPP.apk${NC}"
else
    echo -e "${RED}✗ Android APK: src/apps/android/androidAPP.apk${NC}"
    exit 1
fi

echo -e "${PURPLE}=== Environment Ready ===${NC}" 