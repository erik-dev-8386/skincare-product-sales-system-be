package application.havenskin.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Slf4j
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    // MessageBrokerRegistry là 1 class có Subcribe
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // enableSimpleBroken
        // truyen 1 cai destinnationPrefix
        config.enableSimpleBroker("/haven-skin/get/salesData");
        // định nghĩa các url để FE sẽ subscribe để nhận thông báo từ BE
        // đây là kênh BE gửi dữ liệu tới FE
        config.setApplicationDestinationPrefixes("/haven-skin/send/data");
        // đây là URL mà FE sẽ gửi message đến FE

    }

    // đăng ký các orgin nào để nhận nó
    // thiết lập kết nối
    // ENDPOINT MÀ CLIENT KẾT NỐI ĐẾN WEBSOCKET
    // http:localhost:8080/haven-skin
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/haven-skin/ws").setAllowedOriginPatterns("*").withSockJS();
    }

}
