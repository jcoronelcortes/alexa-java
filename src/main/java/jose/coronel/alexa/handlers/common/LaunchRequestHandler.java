package jose.coronel.alexa.handlers.common;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.LaunchRequest;
import com.amazon.ask.model.RequestEnvelope;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.interfaces.system.SystemState;
import com.amazon.ask.request.Predicates;
import jose.coronel.alexa.util.Util;
import jose.coronel.alexa.gadget.EveGadget;
import org.slf4j.Logger;
import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;

public class LaunchRequestHandler implements RequestHandler {

    private static Logger log = getLogger(LaunchRequestHandler.class);

    @Override
    public boolean canHandle(HandlerInput input) {
        log.debug("Starting LaunchRequestHandler");
        return input.matches(Predicates.requestType(LaunchRequest.class));
    }


    @Override
    public Optional<Response> handle(HandlerInput input) {
        RequestEnvelope envelope = input.getRequestEnvelope();
        SystemState state = envelope.getContext().getSystem();

        EveGadget eveGadget = null;
        try {
            eveGadget = Util.getConnectedEndpoints(state.getApiEndpoint(), state.getApiAccessToken());
        }
        catch (Exception e) {
        }
        if(eveGadget == null || eveGadget.getEndpoints().isEmpty()){
            log.warn("I couldn't find an EV3 Brick connected to this Echo device. Please check to make " +
                    "sure your EV3 Brick is connected, and try again.");
            String speechTextError = "I couldn't find an EV3 Brick connected to this Echo device. Please check to make " +
                    "sure your EV3 Brick is connected, and try again.";
            return input.getResponseBuilder()
                    .withSpeech(speechTextError)
                    .withSimpleCard("mindstorms", speechTextError)
                    .withReprompt(speechTextError)
                    .build();
        }
        else{
            // Store the gadget endpointId to be used in this skill session
            String endpointId = eveGadget.getEndpoints().get(0).getEndpointId();
            log.info("Endpoint Id : {}", endpointId);

            Util.putSessionAttribute(input, "endpointId", endpointId);

            return input.getResponseBuilder()
                    .withSpeech("Welcome, you can start issuing move commands")
                    .withReprompt("Awaiting commands")
                    .build();
        }
    }

}
