package com.momukgback.Controller;

import com.momukgback.Service.Interface.MomukgChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("chats")
@Tag(name = "Introduction to user food menu recommendations", description = "사용자 음식 메뉴 추천 채팅 API")
public class MomukgChatController {

    private final MomukgChatService momukgChatService;

    @Operation(summary = "채팅 요청 API")
    @PostMapping
    public String getCreateChat(@RequestBody String question){
        return momukgChatService.getChatResponse(question);
    }

}
