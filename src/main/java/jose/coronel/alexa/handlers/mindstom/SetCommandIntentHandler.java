package jose.coronel.alexa.handlers.mindstom;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import jose.coronel.alexa.handlers.mindstom.directive.SetCommandIntentDirective;
import jose.coronel.alexa.util.MindstormAlexaUtil;
import org.slf4j.Logger;

import java.util.Locale;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;
import static com.amazon.ask.request.Predicates.requestType;
import static jose.coronel.alexa.util.I18NUtil.getLocale;
import static jose.coronel.alexa.util.I18NUtil.i18n;
import static org.slf4j.LoggerFactory.getLogger;

public class SetCommandIntentHandler implements IntentRequestHandler {

    private static Logger log = getLogger(SetCommandIntentHandler.class);

    @Override
    public boolean canHandle(HandlerInput input, IntentRequest intentRequest) {
        Locale locale = getLocale(input);
        return input.matches(requestType(IntentRequest.class).and(intentName(i18n(locale, "setCommandIntent"))));
    }


    @Override
    public Optional<Response> handle(HandlerInput handlerInput, IntentRequest intentRequest) {
        log.info("Set Command Intent");
        Locale locale = getLocale(handlerInput);
        String command = intentRequest.getIntent().getSlots().get(i18n(locale, "command")).getValue();
        log.info("Comando : {}", command);
        if(command == null){
            return handlerInput.getResponseBuilder()
                    .withSpeech(i18n(locale, "repitSpeech"))
                    .withReprompt(i18n(locale, "repitAgainSpeech"))
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
        log.info("Velocidad : {}", speed);
        // Construct the directive with the payload containing the command parameter
        SetCommandIntentDirective payload = new SetCommandIntentDirective("command", command.replaceAll("ñ","n"), Integer.parseInt(speed));
        String commandSpeech = i18n(locale, "commandSpeech", command);
        log.info("Respuesta de Alexa : '{}'", commandSpeech);
        return handlerInput.getResponseBuilder()
                .withSpeech(commandSpeech)
                .withReprompt(i18n(locale, "reprompt"))
                .addDirective(MindstormAlexaUtil.buildDirective(endpointId, MindstormAlexaUtil.NAMESPACE, MindstormAlexaUtil.NAME_CONTROL, payload))
                .build();
    }

}
