package utils;

import org.yaml.snakeyaml.Yaml;
import java.io.InputStream;
import java.util.Map;

public class ConfigManager {
    private static ConfigManager instance;
    private Map<String, Object> deviceConfig;
    
    private ConfigManager() {
        loadDeviceConfig();
    }
    
    public static ConfigManager getInstance() {
        if (instance == null) {
            synchronized (ConfigManager.class) {
                if (instance == null) {
                    instance = new ConfigManager();
                }
            }
        }
        return instance;
    }
    
    private void loadDeviceConfig() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("device-config.yaml")) {
            if (input != null) {
                Yaml yaml = new Yaml();
                deviceConfig = yaml.load(input);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load device-config.yaml", e);
        }
    }
    
    @SuppressWarnings("unchecked")
    public Map<String, Object> getDeviceCapabilities(String platform) {
        if (deviceConfig == null) {
            throw new RuntimeException("Device configuration not loaded");
        }
        
        Map<String, Object> platformConfig = (Map<String, Object>) deviceConfig.get(platform.toLowerCase());
        if (platformConfig == null) {
            throw new IllegalArgumentException("Platform '" + platform + "' not found in device configuration");
        }
        
        Map<String, Object> capabilities = (Map<String, Object>) platformConfig.get("capabilities");
        if (capabilities == null) {
            throw new RuntimeException("Capabilities not found for platform: " + platform);
        }
        
        return capabilities;
    }
    
    @SuppressWarnings("unchecked")
    public String getServerUrl(String platform) {
        if (deviceConfig == null) {
            throw new RuntimeException("Device configuration not loaded");
        }
        
        Map<String, Object> platformConfig = (Map<String, Object>) deviceConfig.get(platform.toLowerCase());
        if (platformConfig == null) {
            throw new IllegalArgumentException("Platform '" + platform + "' not found in device configuration");
        }
        
        Map<String, Object> serverConfig = (Map<String, Object>) platformConfig.get("server");
        if (serverConfig == null) {
            throw new RuntimeException("Server configuration not found for platform: " + platform);
        }
        
        String url = (String) serverConfig.get("url");
        if (url == null || url.trim().isEmpty()) {
            throw new RuntimeException("Server URL not configured for platform: " + platform);
        }
        
        return url;
    }
    
    @SuppressWarnings("unchecked")
    public Map<String, Object> getTimeouts(String platform) {
        if (deviceConfig == null) {
            throw new RuntimeException("Device configuration not loaded");
        }
        
        Map<String, Object> platformConfig = (Map<String, Object>) deviceConfig.get(platform.toLowerCase());
        if (platformConfig == null) {
            throw new IllegalArgumentException("Platform '" + platform + "' not found in device configuration");
        }
        
        Map<String, Object> timeouts = (Map<String, Object>) platformConfig.get("timeouts");
        if (timeouts == null) {
            throw new RuntimeException("Timeouts not found for platform: " + platform);
        }
        
        return timeouts;
    }
    
    @SuppressWarnings("unchecked")
    public Map<String, Object> getAppConfig(String platform) {
        if (deviceConfig == null) {
            throw new RuntimeException("Device configuration not loaded");
        }
        
        Map<String, Object> platformConfig = (Map<String, Object>) deviceConfig.get(platform.toLowerCase());
        if (platformConfig == null) {
            throw new IllegalArgumentException("Platform '" + platform + "' not found in device configuration");
        }
        
        Map<String, Object> appConfig = (Map<String, Object>) platformConfig.get("app");
        if (appConfig == null) {
            throw new RuntimeException("App configuration not found for platform: " + platform);
        }
        
        return appConfig;
    }
    
    public String getAppPackage(String platform) {
        Map<String, Object> appConfig = getAppConfig(platform);
        return (String) appConfig.get("package");
    }
    
    public String getAppActivity(String platform) {
        Map<String, Object> appConfig = getAppConfig(platform);
        return (String) appConfig.get("activity");
    }
    
    public String getAppPath(String platform) {
        Map<String, Object> appConfig = getAppConfig(platform);
        return (String) appConfig.get("path");
    }
    
    public int getAppInstallTimeout(String platform) {
        Map<String, Object> appConfig = getAppConfig(platform);
        Object timeout = appConfig.get("installTimeout");
        return timeout instanceof Integer ? (Integer) timeout : Integer.parseInt(timeout.toString());
    }
    
    @SuppressWarnings("unchecked")
    public Map<String, Object> getScreenshotConfig(String platform) {
        if (deviceConfig == null) {
            throw new RuntimeException("Device configuration not loaded");
        }
        
        Map<String, Object> platformConfig = (Map<String, Object>) deviceConfig.get(platform.toLowerCase());
        if (platformConfig == null) {
            throw new IllegalArgumentException("Platform '" + platform + "' not found in device configuration");
        }
        
        Map<String, Object> screenshotConfig = (Map<String, Object>) platformConfig.get("screenshot");
        if (screenshotConfig == null) {
            throw new RuntimeException("Screenshot configuration not found for platform: " + platform);
        }
        
        return screenshotConfig;
    }
    
    @SuppressWarnings("unchecked")
    public Map<String, Object> getAllureConfig(String platform) {
        if (deviceConfig == null) {
            throw new RuntimeException("Device configuration not loaded");
        }
        
        Map<String, Object> platformConfig = (Map<String, Object>) deviceConfig.get(platform.toLowerCase());
        if (platformConfig == null) {
            throw new IllegalArgumentException("Platform '" + platform + "' not found in device configuration");
        }
        
        Map<String, Object> allureConfig = (Map<String, Object>) platformConfig.get("allure");
        if (allureConfig == null) {
            throw new RuntimeException("Allure configuration not found for platform: " + platform);
        }
        
        return allureConfig;
    }
} 