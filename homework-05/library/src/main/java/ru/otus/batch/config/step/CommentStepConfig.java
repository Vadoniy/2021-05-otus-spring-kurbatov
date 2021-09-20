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
import ru.otus.batch.converter.CommentConverter;
import ru.otus.domain.mongo.Comment;
import ru.otus.domain.relative.CommentRel;

import javax.persistence.EntityManagerFactory;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class CommentStepConfig {

    private static final String STEP_NAME = "commentItemReader";

    public static final int CHUNK_SIZE = 5;

    private final CommentConverter commentConverter;

    private final EntityManagerFactory entityManagerFactory;

    private final MongoTemplate mongoTemplate;

    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    @StepScope
    public MongoItemReader<Comment> commentReader() {
        return new MongoItemReaderBuilder<Comment>()
                .name(STEP_NAME)
                .collection("COMMENT")
                .template(mongoTemplate)
                .jsonQuery("{}")
                .sorts(Map.of("book", Sort.Direction.ASC))
                .targetType(Comment.class)
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<Comment, CommentRel> commentProcessor() {
        return commentConverter::convert;
    }

    @StepScope
    @Bean
    public JpaItemWriter<CommentRel> commentWriter() {
        return new JpaItemWriterBuilder<CommentRel>()
                .usePersist(false)
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

    @Bean
    public Step migrateCommentsStep(ItemReader<Comment> commentReader, JpaItemWriter<CommentRel> commentWriter,
                                    ItemProcessor<Comment, CommentRel> commentProcessor) {
        return stepBuilderFactory.get("migrate_comments")
                .<Comment, CommentRel>chunk(CHUNK_SIZE)
                .reader(commentReader)
                .processor(commentProcessor)
                .writer(commentWriter)
                .build();
    }
}
