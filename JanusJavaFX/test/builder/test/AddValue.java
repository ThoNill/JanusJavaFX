package builder.test;

import org.janus.actions.Action;
import org.janus.data.DataContext;
import org.janus.data.DataDescription;
import org.janus.dict.actions.ActionDictionary;
import org.janus.dict.actions.NamedActionValue;
import org.janus.gui.actions.ElementConfigurable;
import org.jdom2.Element;

public class AddValue implements Action, ElementConfigurable {
	private int step;
	private NamedActionValue value;
	private String valueName;
	
	public AddValue() {
		super();
	}

	
	@Override
	public void configure(DataDescription description) {
	}

	@Override
	public void perform(DataContext context) {
		if (context == null) {
			return;
		}
		if (value == null) {
			ActionDictionary dict = (ActionDictionary)context.getDataDescription();
			value = dict.getAction(valueName);
		}
		Object obj = value.getObject(context);
		if (obj == null || "".equals(obj)) {
			obj = "0";
		}
		int a = Integer.parseInt(obj.toString()) + step;
		value.setObject(context, a);
		
	}


	@Override
	public void configure(Element elem) {
		step = getInt(elem,"step",1);
		valueName = getString(elem,"value","");
	}

}
