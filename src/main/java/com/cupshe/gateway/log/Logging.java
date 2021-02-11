package com.cupshe.gateway.log;

import com.cupshe.ak.common.BaseConstant;
import com.cupshe.ak.text.StringUtils;
import com.cupshe.gateway.core.HostStatus;
import com.cupshe.gateway.filter.Filters;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.server.reactive.ServerHttpRequest;

/**
 * Logging
 *
 * @author zxy
 */
@Slf4j
public class Logging {

    public static void writeRequestPayload(ServerHttpRequest req, String url) {
        log.info(StringUtils.getFormatString("Rest-gateway forwarding <{} {},{}>",
                req.getMethodValue(), url, req.getHeaders()));
    }

    public static void writeRequestRateLimiter(ServerHttpRequest req) {
        log.info("Rest-gateway request [{}] rate-limiters.", Filters.getPath(req));
    }

    public static void writeRequestUnsupported(ServerHttpRequest req) {
        log.warn("Rest-gateway request [{}] unsupported.", Filters.getPath(req));
    }

    public static void writeRequestNotFound(ServerHttpRequest req) {
        log.warn("Rest-gateway request [{}] not-found.", Filters.getPath(req));
    }

    public static void writeRequestBlacklist(ServerHttpRequest req, String originIp) {
        log.warn("Rest-gateway request [{}] black-list [{}].", Filters.getPath(req), originIp);
    }

    public static void writeRequestUnauthorized(ServerHttpRequest req) {
        log.info("Rest-gateway request [{}] unauthorized.", Filters.getPath(req));
    }

    public static void writeRequestTimeoutBreaker(ServerHttpRequest req, HostStatus hostStatus, String traceId) {
        MDC.put(BaseConstant.MDC_SESSION_KEY, traceId);
        log.error("Rest-gateway request [{}] timeout-breaker [{}].", Filters.getPath(req), hostStatus.getHost());
    }

    public static void writeResponseFailure(Throwable t, String traceId) {
        MDC.put(BaseConstant.MDC_SESSION_KEY, traceId);
        log.error("Gateway error: {}", t.getMessage(), t);
    }
}
