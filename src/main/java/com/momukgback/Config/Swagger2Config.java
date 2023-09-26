package com.momukgback.Config;

import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class Swagger2Config {

    @Bean
    public GroupedOpenApi defaultApi() {
        Info info = new Info().title("모먹지 API").version("v0.1");

        return GroupedOpenApi.builder()
                .group("all")
                .pathsToMatch("/**")
                .displayName("All API")
                .addOpenApiCustomiser(api -> api.setInfo(info))
                .build();
    }

    @Bean
    public GroupedOpenApi userApi() {
        Info info = new Info().title("유저 & 인증/인가 API").version("v0.1");

        return GroupedOpenApi.builder()
                .group("users")
                .pathsToMatch("/users/**")
                .displayName("Users and Authorization")
                .addOpenApiCustomiser(api -> api.setInfo(info))
                .build();
    }

    @Bean
    public GroupedOpenApi StatusApi() {
        Info info = new Info().title("사용자 상태 & 맞춤형 설정 API").version("v0.1");

        return GroupedOpenApi.builder()
                .group("userStatus")
                .pathsToMatch("/UserStatus/**")
                .displayName("User Status & Custom Settings Controller")
                .addOpenApiCustomiser(api -> api.setInfo(info))
                .build();
    }

    @Bean
    public GroupedOpenApi MomukgChatApi() {
        Info info = new Info().title("사용자 음식 메뉴 추천 채팅 API").version("v0.1");

        return GroupedOpenApi.builder()
                .group("MomukgChat")
                .pathsToMatch("/chats/**")
                .displayName("Introduction to user food menu recommendations")
                .addOpenApiCustomiser(api -> api.setInfo(info))
                .build();
    }


}
