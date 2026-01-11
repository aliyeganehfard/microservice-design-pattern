package com.design.pattern.microservicepattern.sec04.orchestratorSequential.util;

import com.design.pattern.microservicepattern.sec04.orchestratorSequential.dto.OrchestrationRequestContext;
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
