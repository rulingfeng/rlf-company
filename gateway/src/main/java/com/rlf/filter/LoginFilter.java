package com.rlf.filter;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Component
public class LoginFilter implements GlobalFilter, Ordered {

    private static final String AUTHORIZE_TOKEN = "accessToken";

    private static AntPathMatcher matcher = new AntPathMatcher();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        // 不需要拦截的url直接放行
        if(needLogin(request.getPath().toString())){
            return chain.filter(exchange);
        }

        String accessToken = request.getHeaders().getFirst(AUTHORIZE_TOKEN);
        if (StringUtils.isBlank(accessToken)) {
            return loginResponse(exchange);
        }

        try {
//            Claims claims = JwtUtil.checkJWT(accessToken);
//            LoginUser loginUser = new LoginUser();
//            loginUser.setUserId((String) claims.get("userId"));
//            loginUser.setMobile((String) claims.get("mobile"));、

            //todo 拿到accessToken然后解析

            // 将accessToken中的信息传入到请求头中，方便后续接口使用
          //  request.mutate().header("LOGIN_USER", JSONObject.toJSONString(loginUser)).build();
        } catch (Exception e) {
            return loginResponse(exchange);
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }

    public static Mono<Void> loginResponse(ServerWebExchange exchange) {
        JSONObject resultJson = new JSONObject();
        resultJson.put("code", 401);
        resultJson.put("message", "请重新登陆授权");
        resultJson.put("status", 401);
        ServerHttpResponse response = exchange.getResponse();
        byte[] bytes = JSONObject.toJSONBytes(resultJson);
        response.getHeaders().add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        DataBuffer buffer = response.bufferFactory().wrap(bytes);
        return response.writeWith(Flux.just(buffer));
    }

    public static boolean needLogin(String uri){
        // test
        List<String> uriList = new ArrayList<>();
        uriList.add("/user/login");
        uriList.add("/order/test/bbb");

        for (String pattern : uriList) {
            if (matcher.match(pattern, uri)) {
                // 不需要拦截
                return true;
            }
        }
        return false;
    }
}