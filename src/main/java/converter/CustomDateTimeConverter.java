package converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.DateTimeConverter;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.faces.Converter;
import org.jboss.seam.annotations.intercept.BypassInterceptors;

@Converter
@BypassInterceptors
@Name("customDateTimeConverter")
public class CustomDateTimeConverter extends DateTimeConverter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		setPattern("HH:mm - dd.MM.yyyy");
		return super.getAsObject(context, component, value);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		setPattern("HH:mm - dd.MM.yyyy");
		return super.getAsString(context, component, value);
	}

}
