package allgemein;

import java.util.Vector;

import javax.swing.JComponent;

import org.janus.data.DataContext;

public class EventActionBinderList {
	Vector<EventActionBinder> list = new Vector<>();

	public EventActionBinderList() {
		
	}

	public void addElement(EventActionBinder obj) {
		list.addElement(obj);
	}

	public void register(JComponent component) {
		for( EventActionBinder b : list) {
			b.register(component);
		}
	}
	
	public void setContext(DataContext context) {
		for( EventActionBinder b : list) {
			b.setContext(context);
		}
	}

	public int size() {
		return list.size();
	}
	
}
