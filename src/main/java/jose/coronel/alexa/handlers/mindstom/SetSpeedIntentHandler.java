package jose.coronel.alexa.handlers.mindstom;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import jose.coronel.alexa.util.Util;

import org.slf4j.Logger;

import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;
import static com.amazon.ask.request.Predicates.requestType;
import static org.slf4j.LoggerFactory.getLogger;


public class SetSpeedIntentHandler implements IntentRequestHandler {

    private static Logger log = getLogger(SetSpeedIntentHandler.class);


    @Override
    public boolean canHandle(HandlerInput input, IntentRequest intentRequest) {
        return input.matches(requestType(IntentRequest.class).and(intentName("SetSpeedIntent")));
    }

    @Override
    public Optional<Response> handle(HandlerInput input, IntentRequest intentRequest) {
        // Bound speed to (1-100)
        String speedText = intentRequest.getIntent().getSlots().get("Speed").getValue();
        int speed = Math.max(1, Math.min(100, Integer.parseInt(speedText)));
        Util.putSessionAttribute(input, "speed", String.valueOf(speed));

        return input.getResponseBuilder()
                .withSpeech("speed set to " + speed + " percent.")
                .withReprompt("awaiting command")
                .build();
    }

}
