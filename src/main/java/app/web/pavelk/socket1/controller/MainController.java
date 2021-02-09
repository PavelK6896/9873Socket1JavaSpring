package app.web.pavelk.socket1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.util.HtmlUtils;

@Controller
@RequiredArgsConstructor
public class MainController {

    @GetMapping
    public String index() {
        return HtmlUtils.htmlEscape("index.html");
    }

}
