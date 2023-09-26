package com.momukgback.Service.Impl;

import com.momukgback.Service.Interface.MomukgChatService;
import io.github.flashvayne.chatgpt.service.ChatgptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MomukgChatServiceImpl implements MomukgChatService {

    private final ChatgptService chatgptService;

    @Override
    public String getChatResponse(String prompt) {
        return chatgptService.sendMessage(prompt);
    }
}
