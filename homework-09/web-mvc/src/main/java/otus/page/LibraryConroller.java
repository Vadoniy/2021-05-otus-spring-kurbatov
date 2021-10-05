package otus.page;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
public class LibraryConroller {

    @GetMapping("/")
    public Mono<String> startPage() {
        return Mono.just("redirect:/book");
    }
}
