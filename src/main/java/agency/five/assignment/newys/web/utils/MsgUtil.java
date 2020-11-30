package agency.five.assignment.newys.web.utils;

import com.vaadin.flow.component.UI;
import org.springframework.context.MessageSource;

import java.util.Locale;

public class MsgUtil {

    private static MessageSource messageSource;
    private static LocaleResolver VAADIN_UI_LOCALE_RESOLVER = () -> UI.getCurrent() != null ? UI.getCurrent().getLocale() : Locale.getDefault();
    private MsgUtil() {

    }

    public static void setMessageSource(MessageSource messageSource) {
        MsgUtil.messageSource = messageSource;
    }

    public static String get(String key) {
        return get(key, (Object[])null);
    }

    public static String get(String key, Object... params) {
        return messageSource.getMessage(key, params, VAADIN_UI_LOCALE_RESOLVER.getLocale());
    }

    /**
     * Defines how to obtain Locale while fetching message from messageSource
     */
    public interface LocaleResolver {
        Locale getLocale();
    }
}
