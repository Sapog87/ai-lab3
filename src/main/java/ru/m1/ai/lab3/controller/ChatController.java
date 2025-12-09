package ru.m1.ai.lab3.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.m1.ai.lab3.dto.ChatRq;
import ru.m1.ai.lab3.service.ChatService;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping
    public String chat(
            @RequestBody @Valid ChatRq chatRq,
            @RequestHeader(value = "memory-id") String memoryId
    ) {
        return chatService.chat(memoryId, chatRq.getMessage());
    }

}
