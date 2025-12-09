package ru.m1.ai.lab3.tool;

import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.rag.content.Content;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.query.Query;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class RecommendationTool {

    private final ContentRetriever gameRetriever;

    @Tool
    public String recommendation(String summary) {
        log.info("RecommendationTool::recommendation {}", summary);

        var result = gameRetriever.retrieve(Query.from(summary))
                .stream()
                .map(Content::textSegment)
                .map(TextSegment::text)
                .collect(Collectors.joining("\n"));

        log.info("RecommendationTool - result \n{}", result);

        if (StringUtils.isBlank(result)) {
            return "nothing";
        }

        return result;
    }

}
