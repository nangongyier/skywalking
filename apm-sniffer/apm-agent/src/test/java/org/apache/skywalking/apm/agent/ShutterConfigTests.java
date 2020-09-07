package org.apache.skywalking.apm.agent;

import org.apache.skywalking.apm.agent.config.TraceShutterConfig;
import org.junit.Test;

public class ShutterConfigTests {
    @Test
    public void getShutterConfig() {
        TraceShutterConfig traceShutterConfig = new TraceShutterConfig();
        String traceSwitch = traceShutterConfig.getTraceSwitch();
        System.out.println(traceSwitch);
    }
}
