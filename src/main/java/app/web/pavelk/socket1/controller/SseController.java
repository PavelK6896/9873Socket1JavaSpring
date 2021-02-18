package app.web.pavelk.socket1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Controller
@RequiredArgsConstructor
public class SseController {

    private final ObjectMapper objectMapper;

    @GetMapping(value = "/get_data")
    public SseEmitter getMyData() throws IOException {
        SseEmitter sseEmitter = new SseEmitter();
        sseEmitter.send(SseEmitter.event().reconnectTime(10000).data(objectMapper.writeValueAsString(LocalDateTime.now())));
        sseEmitter.complete();
        System.out.println("get_data");
        return sseEmitter;
    }

    @GetMapping("/emit_data")
    public SseEmitter fetchData2()
    {
        SseEmitter sseEmitter = new SseEmitter();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() ->
        {
            try {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                sseEmitter.send(objectMapper.writeValueAsString(LocalDateTime.now()));
                sseEmitter.complete();
            } catch (IOException e) {
                sseEmitter.completeWithError(e);
            }
        });
        executorService.shutdown();
        System.out.println("emit_data");
        return sseEmitter;
    }
}
