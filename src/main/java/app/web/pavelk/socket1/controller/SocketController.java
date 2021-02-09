package app.web.pavelk.socket1.controller;


import app.web.pavelk.socket1.dto.MessageDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SocketController {

    final ObjectMapper objectMapper;
    final SimpMessagingTemplate simpMessagingTemplate;

    @GetMapping("/1")
    public String main1() throws JsonProcessingException {
        return objectMapper.writeValueAsString(new SimpleDateFormat("HH:mm:ss").format(new Date()));
    }

    @PostMapping("/2")
    public String main2() throws JsonProcessingException {
        return objectMapper.writeValueAsString(new SimpleDateFormat("HH:mm:ss").format(new Date()));
    }

    @MessageMapping("/chat")
    public void send(MessageDto message) throws JsonProcessingException, InterruptedException {
        System.out.println(message);
        for (int i = 0; i < 100; i++) {
            Thread.sleep(10);
            simpMessagingTemplate.convertAndSend("/topic/messages",
                    objectMapper.writeValueAsString(new SimpleDateFormat("ss").format(new Date()) + " - " + i + " %"));
        }
        simpMessagingTemplate.convertAndSend("/topic/messages", objectMapper.writeValueAsString(message));
    }

    @MessageMapping("/upload")
    public void portfolio(Message<byte[]> message) throws IOException, InterruptedException {
        byte[] payload = message.getPayload();
        System.out.println(" -- upload -- " + payload.length);
        Files.write(Paths.get("file1.txt"), payload, StandardOpenOption.CREATE);
        for (int i = 0; i < 100; i++) {
            Thread.sleep(20);
            simpMessagingTemplate.convertAndSend("/topic/messages",
                    objectMapper.writeValueAsString(new SimpleDateFormat("ss").format(new Date()) + " - " + i + " %"));
        }
        simpMessagingTemplate.convertAndSend("/topic/messages", objectMapper.writeValueAsString("ok"));
    }

}
