package jose.coronel.alexa.handlers.common;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.LaunchRequest;
import com.amazon.ask.model.RequestEnvelope;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.interfaces.system.SystemState;
import com.amazon.ask.request.Predicates;
import jose.coronel.alexa.util.MindstormAlexaUtil;
import jose.coronel.alexa.gadget.EveGadget;
import org.slf4j.Logger;

import java.util.Locale;
import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;
import static jose.coronel.alexa.util.I18NUtil.getLocale;
import static jose.coronel.alexa.util.I18NUtil.i18n;

public class LaunchRequestHandler implements RequestHandler {

    private static Logger log = getLogger(LaunchRequestHandler.class);

    @Override
    public boolean canHandle(HandlerInput input) {
        log.info("Iniciando LaunchRequestHandler");
        return input.matches(Predicates.requestType(LaunchRequest.class));
    }


    @Override
    public Optional<Response> handle(HandlerInput input) {
        Locale locale = getLocale(input);
        RequestEnvelope envelope = input.getRequestEnvelope();
        SystemState state = envelope.getContext().getSystem();

        EveGadget eveGadget = null;
        try {
            eveGadget = MindstormAlexaUtil.getConnectedEndpoints(state.getApiEndpoint(), state.getApiAccessToken());
        }
        catch (Exception e) {
        }
        if(eveGadget == null || eveGadget.getEndpoints().isEmpty()){
            log.error("EV3 no detectado. Informando mensaje de error");
            String speechTextError = i18n(locale, "welcomeError");
            return input.getResponseBuilder()
                    .withSpeech(speechTextError)
                    .withSimpleCard("mindstorms", speechTextError)
                    .withReprompt(speechTextError)
                    .build();
        }
        else{
            log.info("EV3 detectado. Informando mensaje de bienvenida");
            // Store the gadget endpointId to be used in this skill session
            String endpointId = eveGadget.getEndpoints().get(0).getEndpointId();
            log.info("Endpoint Id : {}", endpointId);

            MindstormAlexaUtil.putSessionAttribute(input, "endpointId", endpointId);

            return input.getResponseBuilder()
                    .withSpeech(i18n(locale, "welcome"))
                    .withReprompt(i18n(locale, "reprompt"))
                    .build();
        }
    }

}
