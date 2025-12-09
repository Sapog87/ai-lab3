package ru.m1.ai.lab3;

import com.opencsv.CSVReader;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.store.embedding.EmbeddingStore;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;

import java.io.InputStreamReader;
import java.util.List;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class Lab3Application {

    private final EmbeddingModel embeddingModel;
    private final EmbeddingStore<TextSegment> embeddingStore;

    public static void main(String[] args) {
        SpringApplication.run(Lab3Application.class, args);
    }

    @SneakyThrows
    @EventListener(ApplicationStartedEvent.class)
    public void contextStarted(ApplicationStartedEvent event) {
        try (CSVReader reader = new CSVReader(new InputStreamReader(Lab3Application.class.getResourceAsStream("/dataset/games.csv")))) {

            List<String[]> allData = reader.readAll();

            allData.remove(0);

            List<TextSegment> segments = allData.stream()
                    .map(x -> String.join(",", x))
                    .distinct()
                    .map(x -> StringUtils.substring(x, 0, 1000))
                    .map(TextSegment::from)
                    .toList();

            log.info("embeddingModel: {}", segments.size());

            Response<List<Embedding>> response = embeddingModel.embedAll(segments);

            log.info("embeddingStore");

            embeddingStore.addAll(response.content(), segments);

            log.info("done");
        }
    }

}
