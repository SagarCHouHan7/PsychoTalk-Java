package com.psychotalk.service.AIservices;

import com.psychotalk.model.ChatMessage;
import com.psychotalk.service.Utils.CurrentRoleService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class QuickChatService {

    @Autowired
    private CurrentRoleService currentRoleService;

    private final ChatClient chatClient;
    private static final int MAX_HISTORY = 15;
    private final Map<String, List<ChatMessage>> conversations = new ConcurrentHashMap<>();

    @Value("classpath:/prompts/quick-chat-system-guide.st")
    Resource systemMessage;

    public QuickChatService(ChatClient chatClient){
        this.chatClient = chatClient;
    }


    public Flux<String> getNextResponse(String message) {

        Long userId = currentRoleService.getCurrentUser().getId();
        String conversationId = userId + "_quick";
        conversations.putIfAbsent(conversationId, new ArrayList<>());
        List<ChatMessage> history = conversations.get(conversationId);
        history.add(new ChatMessage("user" , message));

        //resizing the history
        if(history.size() > MAX_HISTORY){
            history = new ArrayList<>(history.subList(history.size() - MAX_HISTORY, history.size()));
            conversations.put(conversationId, history);
        }

        List<Message> messages = history.stream()
                .map(m->
                        m.getRole().equals("user")
                        ? new UserMessage(m.getContent())
                        : new AssistantMessage(m.getContent()))
                .collect(Collectors.toUnmodifiableList());

        //streaming response
        StringBuilder fullResponse = new StringBuilder();

        List<ChatMessage> finalHistory = history;
        return chatClient.prompt()
                .system(systemMessage)
                .messages(messages)
                .stream()
                .content()
                .doOnNext(fullResponse::append)
                .doOnComplete(()->{
                    finalHistory.add(new ChatMessage("assistant" , fullResponse.toString()));
                    conversations.put(conversationId, finalHistory);
                });

    }

    public Boolean reset() {
        conversations.remove(currentRoleService.getCurrentUser().getId()+"_quick");
        return true;
    }
}
