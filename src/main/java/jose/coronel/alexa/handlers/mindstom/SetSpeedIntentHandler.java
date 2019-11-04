package jose.coronel.alexa.handlers.mindstom;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import jose.coronel.alexa.util.MindstormAlexaUtil;

import org.slf4j.Logger;

import java.util.Locale;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;
import static com.amazon.ask.request.Predicates.requestType;
import static jose.coronel.alexa.util.I18NUtil.getLocale;
import static jose.coronel.alexa.util.I18NUtil.i18n;
import static org.slf4j.LoggerFactory.getLogger;


public class SetSpeedIntentHandler implements IntentRequestHandler {

    private static Logger log = getLogger(SetSpeedIntentHandler.class);


    @Override
    public boolean canHandle(HandlerInput input, IntentRequest intentRequest) {
        Locale locale = getLocale(input);
        return input.matches(requestType(IntentRequest.class).and(intentName(i18n(locale, "setSpeedIntent"))));
    }

    @Override
    public Optional<Response> handle(HandlerInput input, IntentRequest intentRequest) {
        log.info("Set Speed Intent");
        Locale locale = getLocale(input);
        // Bound speed to (1-100)
        String speedText = intentRequest.getIntent().getSlots().get(i18n(locale, "speed")).getValue();
        log.info("Velocidad : {}", speedText);
        int speed = Math.max(1, Math.min(100, Integer.parseInt(speedText)));
        MindstormAlexaUtil.putSessionAttribute(input, "speed", String.valueOf(speed));
        String speedSpeech = i18n(locale, "speedSpeech", String.valueOf(speed));
        log.info("Respuesta Alexa : '{}'", speedSpeech);
        return input.getResponseBuilder()
                .withSpeech(speedSpeech)
                .withReprompt(i18n(locale, "reprompt"))
                .build();
    }

}
