package app.web.pavelk.socket1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.util.HtmlUtils;

@Controller
@RequiredArgsConstructor
public class MainController {

    @GetMapping("/i")
    public String index1() {
        return HtmlUtils.htmlEscape("index1.html");
    }

    @GetMapping("/ii")
    public String index2() {
        return HtmlUtils.htmlEscape("index2.html");
    }

    @GetMapping("/iii")
    public String index3() {
        return HtmlUtils.htmlEscape("index3.html");
    }

}
