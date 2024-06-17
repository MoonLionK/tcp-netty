package chc.dts.api.common.lang;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Locale;

/**
 * @author xgy
 * @date 2024/06/13
 */
@Component
public class Langutil {

    @Resource
    private ResourceCache resourceCache;

    public String getMsg(String code) {
        return resourceCache.getMessage(code, null, LocaleContextHolder.getLocale());
    }

    public String getMsg(String code, String locale) {
        String[] locs = locale.split("-");
        String lang = locs[0];
        String country = locs.length > 1 ? locs[1] : "";
        Locale lan = new Locale(lang, country);
        return resourceCache.getMessage(code, null, lan);
    }

    public String getLocale() {
        return LocaleContextHolder.getLocale().toString();
    }
}
