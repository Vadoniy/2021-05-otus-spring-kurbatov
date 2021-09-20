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
import ru.otus.batch.converter.AuthorConverter;
import ru.otus.domain.mongo.Author;
import ru.otus.domain.relative.AuthorRel;

import javax.persistence.EntityManagerFactory;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class AuthorStepConfig {

    private static final String STEP_NAME = "authorItemReader";

    public static final int CHUNK_SIZE = 5;

    private final AuthorConverter authorConverter;

    private final EntityManagerFactory entityManagerFactory;

    private final MongoTemplate mongoTemplate;

    @Bean
    @StepScope
    public MongoItemReader<Author> authorReader() {
        return new MongoItemReaderBuilder<Author>()
                .name(STEP_NAME)
                .collection("AUTHOR")
                .template(mongoTemplate)
                .jsonQuery("{}")
                .targetType(Author.class)
                .sorts(Map.of("name", Sort.Direction.ASC))
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<Author, AuthorRel> authorProcessor() {
        return authorConverter::convert;
    }

    @StepScope
    @Bean
    public JpaItemWriter<AuthorRel> authorWriter() {
        return new JpaItemWriterBuilder<AuthorRel>()
                .usePersist(false)
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Step migrateAuthorsStep(ItemReader<Author> authorReader, JpaItemWriter<AuthorRel> authorWriter,
                                   ItemProcessor<Author, AuthorRel> authorProcessor) {
        return stepBuilderFactory.get("migrate_author")
                .<Author, AuthorRel>chunk(CHUNK_SIZE)
                .reader(authorReader)
                .processor(authorProcessor)
                .writer(authorWriter)
                .build();
    }
}
