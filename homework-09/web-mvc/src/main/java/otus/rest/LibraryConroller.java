package otus.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequiredArgsConstructor
public class LibraryConroller {

    @GetMapping("/")
    public RedirectView startPage() {
        return new RedirectView("/book/list");
    }
}
