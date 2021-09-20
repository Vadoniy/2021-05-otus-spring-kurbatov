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
import ru.otus.batch.converter.BookConverter;
import ru.otus.domain.mongo.Book;
import ru.otus.domain.relative.BookRel;

import javax.persistence.EntityManagerFactory;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class BookStepConfig {

    private static final String STEP_NAME = "bookItemReader";

    public static final int CHUNK_SIZE = 5;

    private final BookConverter bookConverter;

    private final EntityManagerFactory entityManagerFactory;

    private final MongoTemplate mongoTemplate;

    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    @StepScope
    public MongoItemReader<Book> bookReader() {
        return new MongoItemReaderBuilder<Book>()
                .name(STEP_NAME)
                .collection("BOOK")
                .template(mongoTemplate)
                .jsonQuery("{}")
                .targetType(Book.class)
                .sorts(Map.of("genre.genre", Sort.Direction.ASC))
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<Book, BookRel> bookProcessor() {
        return bookConverter::convert;
    }

    @StepScope
    @Bean
    public JpaItemWriter<BookRel> bookWriter() {
        return new JpaItemWriterBuilder<BookRel>()
                .usePersist(false)
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

    @Bean
    public Step migrateBooksStep(ItemReader<Book> bookReader, JpaItemWriter<BookRel> bookWriter,
                                 ItemProcessor<Book, BookRel> bookProcessor) {
        return stepBuilderFactory.get("migrate_book")
                .<Book, BookRel>chunk(CHUNK_SIZE)
                .reader(bookReader)
                .processor(bookProcessor)
                .writer(bookWriter)
                .build();
    }
}
