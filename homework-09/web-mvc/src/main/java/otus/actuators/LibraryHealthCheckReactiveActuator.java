package otus.actuators;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.ReactiveHealthIndicator;
import org.springframework.stereotype.Component;
import otus.repository.BookRepository;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class LibraryHealthCheckReactiveActuator implements ReactiveHealthIndicator {

    private final BookRepository bookRepository;

    @Override
    public Mono<Health> health() {
        return bookRepository.count()
                .map(aLong -> {
                    if (aLong == 0) {
                        return Health.down().withDetail("message", "The library is empty").build();
                    } else {
                        return Health.up().withDetail("message", "Everything is OK.").build();
                    }
                });
    }
}
