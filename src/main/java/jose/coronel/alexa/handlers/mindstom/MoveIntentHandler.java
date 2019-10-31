package jose.coronel.alexa.handlers.mindstom;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import jose.coronel.alexa.handlers.mindstom.directive.MoveIntentDirective;
import jose.coronel.alexa.util.Util;

import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;
import static com.amazon.ask.request.Predicates.requestType;

public class MoveIntentHandler implements IntentRequestHandler {

    @Override
    public boolean canHandle(HandlerInput input, IntentRequest intentRequest) {
        return input.matches(requestType(IntentRequest.class).and(intentName("MoveIntent")));
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput, IntentRequest intentRequest) {
        String direction = intentRequest.getIntent().getSlots().get("Direction").getValue();
        String duration = intentRequest.getIntent().getSlots().get("Duration").getValue();
        if(duration == null || duration.isEmpty()){
            // Duration is optional, use default if not available
            duration = "2";
        }
        String speed = (String) handlerInput.getAttributesManager().getSessionAttributes().get("speed");
        if(speed == null || speed.isEmpty()){
            // speed is optional, use default if not available
            speed = "50";
        }
        String endpointId = (String) handlerInput.getAttributesManager().getSessionAttributes().get("endpointId");
        if(endpointId == null ){
            // endpointId is optional, use default if not available
            endpointId = "";
        }
        // Construct the directive with the payload containing the move parameters
        MoveIntentDirective payload = new MoveIntentDirective("move", direction, Integer.parseInt(duration), Integer.parseInt(speed));

        String speechOutput;
        if(direction.equals("brake")){
            speechOutput = "Applying brake";
        }
        else{
            speechOutput = direction + " " + duration + " seconds at " + speed + " percent speed";
        }

        return handlerInput.getResponseBuilder()
                .withSpeech(speechOutput)
                .withReprompt("awaiting command")
                .addDirective(Util.buildDirective(endpointId, Util.NAMESPACE, Util.NAME_CONTROL, payload))
                .build();
    }

}
