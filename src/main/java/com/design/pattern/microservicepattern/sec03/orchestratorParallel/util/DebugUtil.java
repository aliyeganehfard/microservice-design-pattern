package com.design.pattern.microservicepattern.sec03.orchestratorParallel.util;

import com.design.pattern.microservicepattern.sec03.orchestratorParallel.dto.OrchestrationRequestContext;
import lombok.extern.slf4j.Slf4j;
import tools.jackson.databind.ObjectMapper;

@Slf4j
public class DebugUtil {

    public static void print(OrchestrationRequestContext ctx) {
        var mapper = new ObjectMapper();
        var value = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(ctx);
        log.info(value);
    }
}
