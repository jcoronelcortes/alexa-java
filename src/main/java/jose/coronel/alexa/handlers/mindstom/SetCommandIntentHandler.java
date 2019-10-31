package jose.coronel.alexa.handlers.mindstom;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import jose.coronel.alexa.handlers.mindstom.directive.SetCommandIntentDirective;
import jose.coronel.alexa.util.Util;

import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;
import static com.amazon.ask.request.Predicates.requestType;

public class SetCommandIntentHandler implements IntentRequestHandler {

    @Override
    public boolean canHandle(HandlerInput input, IntentRequest intentRequest) {
        return input.matches(requestType(IntentRequest.class).and(intentName("SetCommandIntent")));
    }


    @Override
    public Optional<Response> handle(HandlerInput handlerInput, IntentRequest intentRequest) {
        String command = intentRequest.getIntent().getSlots().get("Command").getValue();
        if(command == null){
            return handlerInput.getResponseBuilder()
                    .withSpeech("Can you repeat that?")
                    .withReprompt("What was that again?")
                    .build();
        }

        String endpointId = (String) handlerInput.getAttributesManager().getSessionAttributes().get("endpointId");
        if(endpointId == null ){
            // endpointId is optional, use default if not available
            endpointId = "";
        }

        String speed = (String) handlerInput.getAttributesManager().getSessionAttributes().get("speed");
        if(speed == null || speed.isEmpty()){
            // speed is optional, use default if not available
            speed = "50";
        }

        // Construct the directive with the payload containing the command parameter
        SetCommandIntentDirective payload = new SetCommandIntentDirective("command", command, Integer.parseInt(speed));

        return handlerInput.getResponseBuilder()
                .withSpeech("command " + command + " activated")
                .withReprompt("awaiting command")
                .addDirective(Util.buildDirective(endpointId, Util.NAMESPACE, Util.NAME_CONTROL, payload))
                .build();
    }

}
