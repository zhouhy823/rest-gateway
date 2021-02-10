package com.cupshe.gateway.filter;

import com.cupshe.ak.net.UuidUtils;
import com.cupshe.gateway.util.RequestProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

/**
 * MainFilter
 * <p>Cached 'trace-id' and 'remote-host' in serverWebExchange
 *
 * @author zxy
 */
@Component
public class MainFilter extends AbstractFilter {

    private final AbstractFilter next;

    public MainFilter(LimiterFilter limiterFilter) {
        this.next = limiterFilter;
    }

    @Override
    public AbstractFilter next() {
        return next;
    }

    @Override
    public void filter(ServerWebExchange exchange) {
        FilterContext.setTraceId(exchange, UuidUtils.createUuid());
        FilterContext.setRemoteHost(RequestProcessor.getRealOriginIp(exchange));
    }
}
