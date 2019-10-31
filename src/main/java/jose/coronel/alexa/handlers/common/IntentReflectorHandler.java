package jose.coronel.alexa.handlers.common;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;

import java.util.Optional;

public class IntentReflectorHandler implements IntentRequestHandler {

    @Override
    public boolean canHandle(HandlerInput handlerInput, IntentRequest intentRequest) {
        return true;
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput, IntentRequest intentRequest) {
        String intentName = intentRequest.getIntent().getName();
        String speakOutput = "You just triggered " + intentName;
        return handlerInput.getResponseBuilder()
                .withSpeech(speakOutput)
                .withReprompt("I don't understand this command, try again")
                .build();
    }

}
