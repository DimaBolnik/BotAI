package ru.bolnik.botai.api;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatCompletionResponse {
    private List<Choice> choices;
}
