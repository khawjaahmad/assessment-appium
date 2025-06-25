package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import io.qameta.allure.Allure;

/**
 * Centralized logging utility with different log levels and Allure integration
 */
public class LogUtils {
    private static final Logger logger = LogManager.getLogger(LogUtils.class);
    
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String PURPLE = "\u001B[35m";
    private static final String CYAN = "\u001B[36m";
    
    /**
     * Log info level message
     */
    public static void info(String message) {
        logger.info(message);
        System.out.println(GREEN + "[INFO] " + message + RESET);
        Allure.step(message);
    }
    
    /**
     * Log error level message
     */
    public static void error(String message) {
        logger.error(message);
        System.err.println(RED + "[ERROR] " + message + RESET);
        Allure.step(message);
    }
    
    /**
     * Log error with exception
     */
    public static void error(String message, Throwable throwable) {
        logger.error(message, throwable);
        System.err.println(RED + "[ERROR] " + message + " - " + throwable.getMessage() + RESET);
        Allure.step(message + " - " + throwable.getMessage());
    }
    
    /**
     * Log warning level message
     */
    public static void warn(String message) {
        logger.warn(message);
        System.out.println(YELLOW + "[WARN] " + message + RESET);
        Allure.step(message);
    }
    
    /**
     * Log debug level message
     */
    public static void debug(String message) {
        logger.debug(message);
        System.out.println(BLUE + "[DEBUG] " + message + RESET);
    }
    
    /**
     * Log test step information
     */
    public static void step(String stepDescription) {
        logger.info("STEP: " + stepDescription);
        System.out.println(CYAN + "[STEP] " + stepDescription + RESET);
        Allure.step(stepDescription);
    }
    
    /**
     * Log test action
     */
    public static void action(String actionDescription) {
        logger.info("ACTION: " + actionDescription);
        System.out.println(PURPLE + "[ACTION] " + actionDescription + RESET);
        Allure.step(actionDescription);
    }
    
    /**
     * Log verification result
     */
    public static void verification(String verificationDescription, boolean passed) {
        String status = passed ? "PASSED" : "FAILED";
        String color = passed ? GREEN : RED;
        
        logger.info("VERIFICATION " + status + ": " + verificationDescription);
        System.out.println(color + "[VERIFICATION " + status + "] " + verificationDescription + RESET);
        Allure.step(verificationDescription);
    }
    
    /**
     * Log test start
     */
    public static void testStart(String testName) {
        String message = "Starting test: " + testName;
        logger.info("=" + "=".repeat(message.length()) + "=");
        logger.info(message);
        logger.info("=" + "=".repeat(message.length()) + "=");
        
        System.out.println(CYAN + "\n" + "=".repeat(message.length() + 4) + RESET);
        System.out.println(CYAN + "  " + message + "  " + RESET);
        System.out.println(CYAN + "=".repeat(message.length() + 4) + "\n" + RESET);
    }
    
    /**
     * Log test end
     */
    public static void testEnd(String testName, boolean passed) {
        String status = passed ? "PASSED" : "FAILED";
        String color = passed ? GREEN : RED;
        String message = "Test " + status + ": " + testName;
        
        logger.info(message);
        System.out.println(color + "\n" + message + "\n" + RESET);
    }
    
    /**
     * Log page navigation
     */
    public static void pageNavigation(String fromPage, String toPage) {
        String message = "Navigating from " + fromPage + " to " + toPage;
        logger.info(message);
        System.out.println(BLUE + "[NAVIGATION] " + message + RESET);
        Allure.step(message);
    }
    
    /**
     * Log element interaction
     */
    public static void elementInteraction(String action, String element) {
        String message = action + " on " + element;
        logger.info(message);
        System.out.println(PURPLE + "[ELEMENT] " + message + RESET);
        Allure.step(message);
    }
    
    /**
     * Log data entry
     */
    public static void dataEntry(String field, String value) {
        String logValue = isSensitiveData(field) ? "***HIDDEN***" : value;
        String message = "Entering data in " + field + ": " + logValue;
        logger.info(message);
        System.out.println(BLUE + "[DATA] " + message + RESET);
        Allure.step(message);
    }
    
    /**
     * Log configuration information
     */
    public static void config(String configKey, String configValue) {
        String message = "Configuration - " + configKey + ": " + configValue;
        logger.info(message);
        System.out.println(YELLOW + "[CONFIG] " + message + RESET);
    }
    
    /**
     * Check if data is sensitive and should be hidden in logs
     */
    private static boolean isSensitiveData(String fieldName) {
        String lowerField = fieldName.toLowerCase();
        return lowerField.contains("passcode") || 
               lowerField.contains("password") || 
               lowerField.contains("pin") ||
               lowerField.contains("secret") ||
               lowerField.contains("key");
    }
    
    /**
     * Log API or network related information
     */
    public static void network(String message) {
        logger.info("NETWORK: " + message);
        System.out.println(CYAN + "[NETWORK] " + message + RESET);
        Allure.step(message);
    }
    
    /**
     * Log performance metrics
     */
    public static void performance(String metric, long timeMs) {
        String message = metric + " took " + timeMs + "ms";
        logger.info("PERFORMANCE: " + message);
        System.out.println(YELLOW + "[PERFORMANCE] " + message + RESET);
        Allure.step(message);
    }
}