package jose.coronel.alexa.handlers.mindstom;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import jose.coronel.alexa.handlers.mindstom.directive.MoveIntentDirective;
import jose.coronel.alexa.util.MindstormAlexaUtil;
import org.slf4j.Logger;

import java.util.Locale;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;
import static com.amazon.ask.request.Predicates.requestType;
import static jose.coronel.alexa.util.I18NUtil.getLocale;
import static jose.coronel.alexa.util.I18NUtil.i18n;
import static org.slf4j.LoggerFactory.getLogger;

public class MoveIntentHandler implements IntentRequestHandler {

    private static Logger log = getLogger(MoveIntentHandler.class);

    @Override
    public boolean canHandle(HandlerInput input, IntentRequest intentRequest) {
        Locale locale = getLocale(input);
        return input.matches(requestType(IntentRequest.class).and(intentName(i18n(locale, "moveIntent"))));
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput, IntentRequest intentRequest) {
        log.info("Mover Intent");
        Locale locale = getLocale(handlerInput);
        String direction = intentRequest.getIntent().getSlots().get(i18n(locale, "direction")).getValue();
        String duration = intentRequest.getIntent().getSlots().get(i18n(locale, "duration")).getValue();
        log.info("Direccion : {} - Duracion : {}", direction, duration);
        if(duration == null || duration.isEmpty()){
            // Duration is optional, use default if not available
            duration = "2";
        }
        String speed = (String) handlerInput.getAttributesManager().getSessionAttributes().get("speed");
        if(speed == null || speed.isEmpty()){
            // speed is optional, use default if not available
            speed = "50";
        }
        log.info("Velocidad : {}", speed);
        String endpointId = (String) handlerInput.getAttributesManager().getSessionAttributes().get("endpointId");
        if(endpointId == null ){
            // endpointId is optional, use default if not available
            endpointId = "";
        }
        // Construct the directive with the payload containing the move parameters
        MoveIntentDirective payload = new MoveIntentDirective("move", direction, Integer.parseInt(duration), Integer.parseInt(speed));

        String speechOutput;
        if(direction.equals(i18n(locale, "brake"))){
            speechOutput = i18n(locale, "brakeSpeechOutput");
        }
        else{
            speechOutput = i18n(locale, "moveSpeechOutput", direction, duration, speed);
        }
        log.info("Respuesta de Alexa : '{}'", speechOutput);
        return handlerInput.getResponseBuilder()
                .withSpeech(speechOutput)
                .withReprompt(i18n(locale, "reprompt"))
                .addDirective(MindstormAlexaUtil.buildDirective(endpointId, MindstormAlexaUtil.NAMESPACE, MindstormAlexaUtil.NAME_CONTROL, payload))
                .build();
    }

}
