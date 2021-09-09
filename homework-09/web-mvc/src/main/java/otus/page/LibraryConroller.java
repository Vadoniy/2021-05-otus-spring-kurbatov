package otus.page;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class LibraryConroller {

    @GetMapping("/")
    public String startPage() {
        return "redirect:" + "/book/list";
    }
}
