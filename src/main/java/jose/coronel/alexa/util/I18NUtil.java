package jose.coronel.alexa.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class I18NUtil {

    public static final Logger log = LoggerFactory.getLogger(I18NUtil.class);
    public static final String BUNDLENAME = "translations";
    public static final Locale DEFAULT_LOCALE = new Locale("en", "US");


    public static Locale getLocale(HandlerInput input) {
        String localeStr = input.getRequest().getLocale();
        log.debug("Locale ingresado : {}", localeStr);
        Locale locale = Locale.forLanguageTag(localeStr);
        log.debug("Local detectado : {}", locale);
        return locale;
    }


    public static String i18n(Locale locale, String key, String... parameters) {
        if (locale == null) {
            log.warn("Locale no definido. Usando Locale por defecto : {}", DEFAULT_LOCALE);
            locale = DEFAULT_LOCALE;
        }
        log.debug("Usando Locale : {}_{}", locale.getLanguage(), locale.getCountry());
        String i18nMessage = "";
        try {
            ResourceBundle labels = ResourceBundle.getBundle("i18n." + BUNDLENAME, locale);
            MessageFormat formatter = new MessageFormat("");
            formatter.setLocale(locale);
            formatter.applyPattern(labels.getString(key));
            i18nMessage = formatter.format(parameters);
        }
        catch (Exception e) {
            log.error("No se puede formatear el mensaje i18n para la llave {}", key, e);
            i18nMessage = key;
        }
        return i18nMessage;
    }

}
