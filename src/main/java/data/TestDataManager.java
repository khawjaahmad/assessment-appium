package data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.util.Map;

public class TestDataManager {
    private static TestDataManager instance;
    private Map<String, Object> testData;
    private final ObjectMapper objectMapper;
    
    private TestDataManager() {
        objectMapper = new ObjectMapper();
        loadTestData();
    }
    
    public static TestDataManager getInstance() {
        if (instance == null) {
            synchronized (TestDataManager.class) {
                if (instance == null) {
                    instance = new TestDataManager();
                }
            }
        } 
        return instance;
    }
    
    private void loadTestData() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("test-data.json")) {
            if (input != null) {
                testData = objectMapper.readValue(input, new TypeReference<Map<String, Object>>() {});
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load test-data.json", e);
        }
    }
    
    @SuppressWarnings("unchecked")
    public String getPasscode(String type) {
        Map<String, Object> passcodes = (Map<String, Object>) testData.get("passcodes");
        if (passcodes == null) {
            throw new RuntimeException("Passcodes section not found in test data");
        }
        
        String passcode = (String) passcodes.get(type);
        if (passcode == null) {
            throw new IllegalArgumentException("Passcode type '" + type + "' not found in test data");
        }
        
        return passcode;
    }
    
    public String getValidPasscode() {
        return getPasscode("valid");
    }
    
    public String getShortPasscode() {
        return getPasscode("short");
    }
    
    public String[] getMismatchedPasscodes() {
        return new String[]{getPasscode("first"), getPasscode("second")};
    }
    
    @SuppressWarnings("unchecked")
    public Map<String, Integer> getTimeouts() {
        Map<String, Integer> timeouts = (Map<String, Integer>) testData.get("timeouts");
        if (timeouts == null) {
            throw new RuntimeException("Timeouts section not found in test data");
        }
        return timeouts;
    }
    
    public int getTimeout(String type) {
        Map<String, Integer> timeouts = getTimeouts();
        Integer timeout = timeouts.get(type);
        if (timeout == null) {
            throw new IllegalArgumentException("Timeout type '" + type + "' not found in test data");
        }
        return timeout;
    }
} 