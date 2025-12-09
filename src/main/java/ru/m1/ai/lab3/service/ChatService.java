package ru.m1.ai.lab3.service;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

public interface ChatService {

    @SystemMessage(fromResource = "prompts/system.prompt")
    String chat(@MemoryId Object memoryId, @UserMessage String message);

}
