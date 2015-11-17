package builder.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import javafx.application.Application;
import javafx.stage.Stage;

import org.janus.binder.BindWalker;
import org.janus.binder.gui.GuiBuilderWalker;
import org.janus.builder.BuilderWalker;
import org.janus.data.DataContext;
import org.janus.gui.basis.GuiComponent;
import org.janus.gui.basis.JanusApplication;
import org.janus.gui.basis.JanusPage;
import org.janus.gui.basis.JanusSession;
import org.janus.gui.builder.GuiElementBuilder;
import org.janus.gui.javafx.JavaFXBasisConnector;
import org.janus.gui.javafx.StageConnector;
import org.janus.gui.javafx.builder.JavaFXGuiElementBuilder;
import org.jdom2.Document;

import allgemein.SessionInterface;
import toni.druck.xml.XMLDocumentLoader;

public class TestSinglePage extends Application {
	private String pagename;

	
	public TestSinglePage(String pagename) {
		this.pagename = pagename;
	}

	@Override
	public void start(Stage primaryStage) {
		GuiElementBuilder elementBuilder = new JavaFXGuiElementBuilder();
		Document page = new XMLDocumentLoader().createDocument("pages/"
				+ pagename + ".xml");

	
		JanusApplication app = new JanusApplication("test");
		JanusPage dict = new JanusPage("test");
		
		BuilderWalker walker = new TestBuilderWalker(pagename);
		walker.setDict(dict);
		walker.walkAlong(page);
		
		BindWalker bindWalker = new TestBinderWalker();
		bindWalker.walkAlong(page);
		bindWalker.bind(dict);
		
		GuiBuilderWalker guiWalker = new GuiBuilderWalker(elementBuilder);
		guiWalker.setDict(dict);
		guiWalker.walkAlong(page);
		app.addPage(dict);

		GuiComponent comp = guiWalker.getRoot();

		assertNotNull(comp);
		assertTrue(comp instanceof StageConnector);

		JanusSession session = new JanusSession(app);
		SessionInterface.setSession(session);

		DataContext context = session.createContextInSession(dict);

		for (GuiComponent c : guiWalker.getComponents()) {
			((JavaFXBasisConnector) c).setContext(context);
		}
		StageConnector frameConnector = (StageConnector) comp;
		frameConnector.setContext(context);

		primaryStage.setScene(frameConnector.getScene());
		primaryStage.show();

	}

}