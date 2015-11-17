package allgemein;

import java.util.concurrent.LinkedBlockingQueue;

import org.janus.actions.Action;
import org.janus.data.DataContext;
import org.janus.gui.basis.JanusSession;
import org.janus.gui.basis.PageContext;

public class SessionInterface extends Thread {

	private static JanusSession session;
	private static LinkedBlockingQueue<ActionWithContext> queue = new LinkedBlockingQueue<ActionWithContext>();
	private static SessionInterface si = new SessionInterface();

	public static void setSession(JanusSession session) {
		SessionInterface.session = session;
	}

	public static PageContext getContext(String name) {
		int index = session.getHandle(name);
		return (PageContext)session.getObject(index);
	}	
	
	private SessionInterface() {
		super("SessionInterface");
		start();
	}

	@Override
	public void run() {

		try {
			for (;;) {
				ActionWithContext a = queue.take();
				try {
					a.actionPerformed();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void performAction(Action action,DataContext context) {
		ActionWithContext saction = new ActionWithContext(context, action);
		queue.add(saction);
		
	}

}
