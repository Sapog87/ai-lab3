package ru.m1.ai.lab3.config;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.pgvector.PgVectorEmbeddingStore;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import ru.m1.ai.lab3.service.ChatService;
import ru.m1.ai.lab3.tool.RecommendationTool;

@Configuration
public class AssistantConfiguration {

    @Bean
    public ChatMemoryProvider chatMemoryProvider() {
        return memoryId -> MessageWindowChatMemory.withMaxMessages(10);
    }

    @Bean
    public ChatService chatService(
            ChatMemoryProvider chatMemoryProvider,
            ChatModel chatModel,
            RecommendationTool recommendationTool
    ) {
        return AiServices.builder(ChatService.class)
                .chatMemoryProvider(chatMemoryProvider)
                .chatModel(chatModel)
                .tools(recommendationTool)
                .build();
    }

    @Bean
    public EmbeddingStore<TextSegment> embeddingStore(
            PgVectorConfigurationProperties properties,
            EmbeddingModel embeddingModel
    ) {
        return PgVectorEmbeddingStore.builder()
                .host(properties.getHost())
                .port(properties.getPort())
                .database(properties.getDatabase())
                .user(properties.getUser())
                .password(properties.getPassword())
                .table(properties.getTable())
                .dimension(embeddingModel.dimension())
                .build();
    }

    @Bean
    public ContentRetriever contentRetriever(EmbeddingStore<TextSegment> embeddingStore, EmbeddingModel embeddingModel) {
        return EmbeddingStoreContentRetriever.builder()
                .embeddingStore(embeddingStore)
                .embeddingModel(embeddingModel)
                .maxResults(10)
                .minScore(0.5)
                .build();
    }

}
