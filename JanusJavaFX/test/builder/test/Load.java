package builder.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.log4j.Logger;
import org.janus.binder.BindWalker;
import org.janus.builder.BuilderWalker;
import org.janus.dict.actions.ActionDictionary;
import org.janus.dict.actions.ActionEventSender;
import org.jdom2.Document;
import org.junit.Assert;
import org.junit.Test;

import toni.druck.xml.XMLDocumentLoader;

public class Load {
    private static final Logger LOG = Logger.getLogger(Load.class);


	@Test
	public void loadDocument() {
		try {
			Document page = new XMLDocumentLoader()
					.createDocument("pages/TestPage.xml");
			assertNotNull(page);

		} catch (Exception e) {
		    LOG.error("echte Ausnahme",e);
			Assert.fail("Exception " + e.getMessage());
		}
	}
	
	@Test
	public void walkDocument() {
		try {
			Document page = new XMLDocumentLoader()
					.createDocument("pages/TestPage.xml");
			BuilderWalker walker = new TestBuilderWalker("TestPage");
			walker.setDict(new ActionDictionary("test"));
			walker.walkAlong(page);
			assertNotNull(page);

		} catch (Exception e) {
		    LOG.error("echte Ausnahme",e);
			Assert.fail("Exception " + e.getMessage());
		}
	}

	
	@Test
	public void loadAndBind() {
		try {
			Document page = new XMLDocumentLoader()
					.createDocument("pages/BindPage.xml");
			BuilderWalker walker = new TestBuilderWalker("TestPage");
			ActionDictionary dict = new ActionDictionary("test");
			walker.setDict(dict);
			walker.walkAlong(page);
			
			BindWalker bindWalker = new TestBinderWalker();
			bindWalker.walkAlong(page);
			bindWalker.bind(dict);			
			
			ActionEventSender bean = dict.getAction("bean");
			ActionEventSender call = dict.getAction("start");
			ActionEventSender s1 = dict.getAction("string1");
			ActionEventSender s2 = dict.getAction("string2");
			ActionEventSender s3 = dict.getAction("string3");
		
			assertEquals(1,call.getListenersCount());	
			assertEquals(1,s1.getListenersCount());	
			assertEquals(2,s2.getListenersCount());	
			assertEquals(2,s3.getListenersCount());	
	
			assertNotNull(page);

		} catch (Exception e) {
		    LOG.error("echte Ausnahme",e);
			Assert.fail("Exception " + e.getMessage());
		}
	}	
	
}
