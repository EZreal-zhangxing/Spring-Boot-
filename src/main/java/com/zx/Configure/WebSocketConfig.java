package com.zx.Configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @Auother zhangxing
 * @Date 2019-05-23 16:14
 * @note
 */
@Configuration
public class WebSocketConfig {
    @Bean
    public ServerEndpointExporter getServerExporter(){
       return new ServerEndpointExporter();
    }
}
