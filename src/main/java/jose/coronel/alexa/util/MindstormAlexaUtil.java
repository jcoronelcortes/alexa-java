package jose.coronel.alexa.util;

import com.amazon.ask.attributes.AttributesManager;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.interfaces.customInterfaceController.SendDirectiveDirective;
import com.amazon.ask.model.interfaces.customInterfaceController.Header;
import com.amazon.ask.model.interfaces.customInterfaceController.Endpoint;
import com.fasterxml.jackson.databind.ObjectMapper;
import jose.coronel.alexa.gadget.EveGadget;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.Response;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

public class MindstormAlexaUtil {

    private static Logger log = getLogger(MindstormAlexaUtil.class);

    public static String NAMESPACE = "Custom.Mindstorms.Gadget";
    public static String NAME_CONTROL = "control";


    public static EveGadget getConnectedEndpoints(String apiEndpoint, String apiAccessToken) throws Exception {
        log.info("Obteniendo endpoint del EV3 conectado");
        String url = apiEndpoint + "/v1/endpoints";
        log.info("URL : {}", url);
        log.info("Api Access Token : {}", apiAccessToken);
        AsyncHttpClient asyncHttpClient = new DefaultAsyncHttpClient();
        CompletableFuture<Response> response = asyncHttpClient
                .prepareGet(url)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + apiAccessToken)
                .execute()
                .toCompletableFuture();

        String jsonResponse = null;
        try {
            jsonResponse = response.get().getResponseBody();
        } catch (InterruptedException e) {
            log.error("Mensaje de error : {}", e.getMessage(), e);
            throw new Exception(e);
        } catch (ExecutionException e) {
            log.error("Mensaje de error : {}", e.getMessage(), e);
            throw new Exception(e);
        }
        log.info("Respuesta del EV3 : {}",  jsonResponse);
        try {
            return new ObjectMapper().readValue(jsonResponse, EveGadget.class);
        } catch (IOException e) {
            log.error("Mensaje de error : {}", e.getMessage(), e);
            throw new Exception(e);
        }
    }


    public static void putSessionAttribute(HandlerInput input, String key, String value) {
        AttributesManager attributesManager = input.getAttributesManager();
        Map<String, Object> sessionAttributes = attributesManager.getSessionAttributes();
        sessionAttributes.put(key, value);
        attributesManager.setSessionAttributes(sessionAttributes);
    }


    public static SendDirectiveDirective buildDirective(String endpointId, String namespace, String name, Object payload){
        return SendDirectiveDirective
                .builder()
                .withHeader(Header.builder().withName(name).withNamespace(namespace).build())
                .withEndpoint(Endpoint.builder().withEndpointId(endpointId).build())
                .withPayload(payload)
                .build();
    }

}
