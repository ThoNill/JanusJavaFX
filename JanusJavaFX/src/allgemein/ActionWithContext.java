package allgemein;

import org.janus.actions.Action;

import org.janus.data.DataContext;

public class ActionWithContext {
	private DataContext context;
	private Action action;


	public ActionWithContext(DataContext session, 
			Action action) {
		super();
		this.context = session;
		this.action = action;
	}

	public void actionPerformed() {
		try {
			action.perform(context);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}


}
