package io.github.kimmking.gateway.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;

/**
 * @author kin_lan
 */
public class HttpInboundRequestFilter implements  HttpRequestFilter{

    public static class HttpInboundRequestFilterHolder{

        private final static HttpInboundRequestFilter INSTANCE = new HttpInboundRequestFilter();
    }

    @Override
    public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        HttpHeaders headers = fullRequest.headers();
        headers.add("nio", "kin_lan");
    }

    public HttpInboundRequestFilter getInstance(){
        return HttpInboundRequestFilterHolder.INSTANCE;
    }

}
