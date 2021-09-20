package ru.otus.batch.config.step;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.data.builder.MongoItemReaderBuilder;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.batch.converter.GenreConverter;
import ru.otus.domain.mongo.Genre;
import ru.otus.domain.relative.GenreRel;

import javax.persistence.EntityManagerFactory;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class GenreStepConfig {

    private static final String STEP_NAME = "genreItemReader";

    public static final int CHUNK_SIZE = 5;

    private final GenreConverter genreConverter;

    private final EntityManagerFactory entityManagerFactory;

    private final MongoTemplate mongoTemplate;

    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    @StepScope
    public MongoItemReader<Genre> genreReader() {
        return new MongoItemReaderBuilder<Genre>()
                .name(STEP_NAME)
                .collection("GENRE")
                .template(mongoTemplate)
                .jsonQuery("{}")
                .targetType(Genre.class)
                .sorts(Map.of("genre", Sort.Direction.ASC))
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<Genre, GenreRel> genreProcessor() {
        return genreConverter::convert;
    }

    @StepScope
    @Bean
    public JpaItemWriter<GenreRel> genreWriter() {
        return new JpaItemWriterBuilder<GenreRel>()
                .usePersist(false)
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

    @Bean
    public Step migrateGenresStep(ItemReader<Genre> genreReader, JpaItemWriter<GenreRel> genreWriter,
                                  ItemProcessor<Genre, GenreRel> genreProcessor) {
        return stepBuilderFactory.get("migrate_genre")
                .<Genre, GenreRel>chunk(CHUNK_SIZE)
                .reader(genreReader)
                .processor(genreProcessor)
                .writer(genreWriter)
                .build();
    }
}
