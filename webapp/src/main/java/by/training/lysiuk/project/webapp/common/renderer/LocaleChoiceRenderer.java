package by.training.lysiuk.project.webapp.common.renderer;

import java.util.Locale;

import org.apache.wicket.Localizer;
import org.apache.wicket.markup.html.form.ChoiceRenderer;

import by.training.lysiuk.project.webapp.app.WicketApplication;

public class LocaleChoiceRenderer extends ChoiceRenderer<Locale> {

    public static final LocaleChoiceRenderer INSTANCE = new LocaleChoiceRenderer();

    private LocaleChoiceRenderer() {
        super();
    }

    @Override
    public Object getDisplayValue(Locale object) {
        Localizer localizer = WicketApplication.get().getResourceSettings().getLocalizer();
        String localizedValue = localizer.getString(object.getCountry(), null);
        return localizedValue;
    }

    @Override
    public String getIdValue(Locale object, int index) {
        return object.getLanguage();
    }
}
