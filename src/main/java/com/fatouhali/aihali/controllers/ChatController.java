package com.fatouhali.aihali.controllers;

import com.fatouhali.aihali.entity.Depenses;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Media;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.ai.openai.api.OpenAiImageApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@RestController
public class ChatController {

    private final ChatClient chatClient;

    @Value("classpath:/prompts/system_message.st")
    private Resource systemMessage;

    public ChatController(ChatClient.Builder chatClient) {
        this.chatClient = chatClient.build();
    }

    @GetMapping(value = "/chat", produces = MediaType.TEXT_PLAIN_VALUE)
    public String Chat(String request){
   String response =  chatClient.prompt()
           .system("")
             .user(request)
             .call()
             .content();
             return response;

    }
    @GetMapping(value = "/chat2", produces = MediaType.TEXT_PLAIN_VALUE)
    public Flux<String> stream(String request){
        Flux<String> response =  chatClient.prompt()
                .system("")
                .user(request)
                .stream()
                .content();
        return response;

    }

    @GetMapping(value = "/msg", produces = MediaType.TEXT_PLAIN_VALUE)
    public String message(String rq){
        String response =  chatClient.prompt()
                .system(systemMessage)
                .user(rq)
                .call()
                .content();
        return response;

    }

    @GetMapping(value = "/depense")
    public Depenses depense() throws IOException {

        byte[] data = new ClassPathResource("depense2.jpg").getContentAsByteArray();

      String  userMessageText = """
              Tob  rôle est de faire reconnaissance optique du texte
              qui se trouve dans l'image fournie
              calculé moin le reste de depenses
              """;
        UserMessage userMessage = new UserMessage(userMessageText,
                List.of(
                       new Media(MimeTypeUtils.IMAGE_JPEG, data)
                ));

        return chatClient.prompt().messages(userMessage).call().entity(Depenses.class);
    }

   @GetMapping(value = "generateImage", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] generateImage() throws IOException{
        OpenAiImageApi aiImageApi = new OpenAiImageApi("sk-proj-41hofZNlwQuBUpkuM2FhT3BlbkFJcyUXBXlklLdXyKe2yLMB");
        OpenAiImageModel openAiImageModel = new OpenAiImageModel(aiImageApi);
//deux petite filles africaine dans le foret a coté de une biche
        ImageResponse response = openAiImageModel.call(
                new ImagePrompt("coffrage de un poteaux 20x20x100",
                        OpenAiImageOptions.builder()
                                .withModel("dall-e-3")
                                .withQuality("hd")
                                .withN(1)
                                .withResponseFormat("b64_json")
                                .withHeight(1024)
                                .withWidth(1024).build())
        );
String image = response.getResult().getOutput().getB64Json();
byte[] decode = Base64.getDecoder().decode(image);
return decode;
    }
}
