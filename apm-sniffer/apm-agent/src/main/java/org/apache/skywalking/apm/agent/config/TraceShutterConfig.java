package org.apache.skywalking.apm.agent.config;

import cn.fraudmetrix.shutter.client.common.annotations.ShutterFile;
import cn.fraudmetrix.shutter.client.common.annotations.ShutterFileItem;
import org.springframework.stereotype.Service;

@Service
@ShutterFile(filename = "octopus-skywalking.properties", observable = true)
public class TraceShutterConfig {
    @ShutterFileItem(name = "trace.switch")
    private String traceSwitch = "true";

    public String getTraceSwitch() {
        return traceSwitch;
    }

    public void setTraceSwitch(String traceSwitch) {
        this.traceSwitch = traceSwitch;
    }
}
