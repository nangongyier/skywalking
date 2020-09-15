package org.apache.skywalking.apm.webapp.config;

import cn.tongdun.config.client.ConfigClient;
import cn.tongdun.config.client.constants.ConfigType;
import cn.tongdun.config.client.model.ConfigMetadata;
import cn.tongdun.config.client.observer.ConfigChangeListener;
import cn.tongdun.config.file.utils.PropertiesUtils;
import cn.tongdun.config.spring.ConfigRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Properties;

// 使用@PostConstruct注解，需要添加@Service注解
@Service
public class ShutterConfig implements ConfigChangeListener {
    private final static Logger logger = LoggerFactory.getLogger(ShutterConfig.class);

    @Autowired
    private ConfigRepository repository;

    private String traceSwitch;

    public String getTraceSwitch() {
        return traceSwitch;
    }

    public void setTraceSwitch(String traceSwitch) {
        this.traceSwitch = traceSwitch;
    }

    @PostConstruct
    public void init() throws Exception {
        // 以下是监听的demo
        if (!repository.isDisabled()) {
            ConfigClient client      = repository.getClient();
            String       appId       = repository.getApplication();
            String       environment = repository.getEnvironment();
            ConfigMetadata metadata = client.metadata()
                    .from(appId, "DEFAULT_GROUP", environment)
                    .fetch("octopus-skywalking.properties", ConfigType.FILE)
                    .build();
            onChange(metadata);
        }
    }

    @Override
    public void onChange(ConfigMetadata metadata) throws Exception {
        if (repository.isDisabled()) {
            return;
        }

        ConfigClient client = repository.getClient();
        Properties properties = client.file().getProperties(metadata,this);
        logger.info("===========shutter file change : app={}, env={}, group={}, new propertes={}", metadata.getApp(), metadata.getEnv(), metadata.getGroup(), PropertiesUtils.dump(properties));
    }
}
