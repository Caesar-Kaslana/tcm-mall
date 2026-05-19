package com.tcmmall.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcmmall.vo.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.*;

@RestController
@RequestMapping("/api/ai")
public class AiController {

    @Value("${deepseek.api.key}")
    private String apiKey;

    @Value("${deepseek.api.url}")
    private String apiUrl;

    @Value("${deepseek.api.model}")
    private String model;

    private static final HttpClient client = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    private static final ObjectMapper mapper = new ObjectMapper();

    private static final String SYSTEM_PROMPT = "你是中医商城（TCM Mall）的智能助手，精通中医药知识，也熟悉本商城的使用方法。" +
            "你的能力包括：1. 中医药知识：解答中药功效、禁忌、用法用量、养生建议等；" +
            "2. 商城帮助：指导用户注册登录、浏览商品、下单购物、管理订单等操作。" +
            "回答要求：用中文回答，简洁友好；" +
            "如果是中医健康问题，补充\"以上内容仅供参考，具体用药请咨询专业医师\"；" +
            "如果问题超出范围，礼貌引导用户回到中医或商城相关话题。";

    @PostMapping("/chat")
    public Result<Map<String, Object>> chat(@RequestBody Map<String, Object> body) {
        try {
            String message = (String) body.get("message");
            if (message == null || message.isBlank()) {
                return Result.error(400, "请输入问题");
            }

            @SuppressWarnings("unchecked")
            List<Map<String, String>> history = (List<Map<String, String>>) body.get("history");

            List<Map<String, String>> messages = new ArrayList<>();
            messages.add(Map.of("role", "system", "content", SYSTEM_PROMPT));

            if (history != null) {
                for (Map<String, String> msg : history) {
                    messages.add(Map.of(
                            "role", msg.get("role"),
                            "content", msg.get("content")
                    ));
                }
            }

            messages.add(Map.of("role", "user", "content", message));

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", model);
            requestBody.put("messages", messages);
            requestBody.put("temperature", 0.7);
            requestBody.put("max_tokens", 2000);

            String jsonBody = mapper.writeValueAsString(requestBody);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + apiKey)
                    .timeout(Duration.ofSeconds(30))
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                Map<String, Object> respMap = mapper.readValue(response.body(), Map.class);
                @SuppressWarnings("unchecked")
                List<Map<String, Object>> choices = (List<Map<String, Object>>) respMap.get("choices");
                if (choices != null && !choices.isEmpty()) {
                    @SuppressWarnings("unchecked")
                    Map<String, String> msg = (Map<String, String>) choices.get(0).get("message");
                    String reply = msg.get("content");
                    Map<String, Object> data = new HashMap<>();
                    data.put("reply", reply);
                    return Result.success(data);
                }
            }

            return Result.error(500, "AI 服务响应异常，请稍后再试");
        } catch (Exception e) {
            return Result.error(500, "AI 服务暂时不可用：" + e.getMessage());
        }
    }
}
