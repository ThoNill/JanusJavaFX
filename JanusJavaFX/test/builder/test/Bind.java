package builder.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.apache.log4j.Logger;
import org.janus.actions.EmptyAction;
import org.janus.binder.BindAdvice;
import org.janus.binder.BindAdviceList;
import org.janus.binder.BindWalker;
import org.janus.binder.ElementBinder;
import org.janus.binder.ElementBinderList;
import org.janus.dict.actions.ActionDictionary;
import org.janus.dict.actions.ActionEventSender;
import org.jdom2.Document;
import org.jdom2.Element;
import org.junit.Assert;
import org.junit.Test;

import toni.druck.xml.XMLDocumentLoader;

public class Bind {
    private static final Logger LOG = Logger.getLogger(Bind.class);


	@Test
	public void bindAdvice1() {
	   
		ActionDictionary dict = new ActionDictionary("test");
		dict.addAction("name", new EmptyAction());
		ActionEventSender sender = dict.addAction("action1", new EmptyAction());
				
		try {
			BindAdvice a = new BindAdvice("name", "action");
			a.bind(dict);
			fail("Exception erwartet");
		} catch (RuntimeException ex) {
		    LOG.error("provozierte Ausnahme",ex);
		}
	}

	@Test
	public void bindAdvice2() {
		ActionDictionary dict = new ActionDictionary("test");
		dict.addAction("name", new EmptyAction());
		ActionEventSender sender = dict.addAction("action1", new EmptyAction());

		BindAdvice a = new BindAdvice("name", "action1");
		a.bind(dict);

		assertEquals(1, sender.getListenersCount());
	}
	
	@Test
	public void bindAdvice3() {
		ActionDictionary dict = new ActionDictionary("test");
		dict.addAction("name", new EmptyAction());
		ActionEventSender sender1 = dict.addAction("action1", new EmptyAction());
		ActionEventSender sender2 = dict.addAction("action2", new EmptyAction());

		BindAdviceList aList = new BindAdviceList();
		aList.addAdviceKommaList("name", "action1,action2");
		aList.bind(dict);
		
		
		assertEquals(1, sender1.getListenersCount());
		assertEquals(1, sender2.getListenersCount());
	}

	
	@Test
	public void bindAdvice4() {
		ActionDictionary dict = new ActionDictionary("test");
		dict.addAction("name", new EmptyAction());
		ActionEventSender sender1 = dict.addAction("action1", new EmptyAction());
		ActionEventSender sender2 = dict.addAction("action2", new EmptyAction());

		BindAdviceList aList = new BindAdviceList();
		aList.addAdviceQList("name", "Das ist ?action1? und ?action2? ");
		aList.bind(dict);
		
		
		assertEquals(1, sender1.getListenersCount());
		assertEquals(1, sender2.getListenersCount());
	}
	
	@Test
	public void bindAdvice5() {
		Element element = new Element("STRING");
		element.setAttribute("name","test");
		element.setAttribute("source","action1,action2");
	
		BindAdviceList list = new BindAdviceList();
		
		ElementBinder eb = new ElementBinder("STRING", "name", "source");
		eb.setList(list);
		
		eb.addBindAdvices(element);
		assertEquals(2,list.size());
	}	
	
	@Test
	public void bindAdvice6() {
		Element element = new Element("STRING");
		element.setAttribute("name","test");
		element.setAttribute("source","action1,action2");
	
		BindAdviceList list = new BindAdviceList();
		
		ElementBinder eb = new ElementBinder("STRING", "name", "source");
		
		ElementBinderList bList = new ElementBinderList();
		bList.add(eb);
		
		bList.setList(list);
		
		bList.addBindAdvices(element,element);
		
			
		assertEquals(2,list.size());	
	}		

	@Test
	public void bindAdvice7() {
		try {
			Document page = new XMLDocumentLoader()
					.createDocument("pages/BindPage.xml");
			
			ActionDictionary dict = new ActionDictionary("test");
			
			ActionEventSender bean = dict.addAction("bean", new EmptyAction());
			ActionEventSender call = dict.addAction("start", new EmptyAction());
			ActionEventSender s1 = dict.addAction("string1", new EmptyAction());
			ActionEventSender s2 = dict.addAction("string2", new EmptyAction());
			ActionEventSender s3 = dict.addAction("string3", new EmptyAction());

			BindWalker walker = new TestBinderWalker();
			walker.walkAlong(page);
			walker.bind(dict);
			
			assertEquals(1,call.getListenersCount());	
			assertEquals(1,s1.getListenersCount());	
			assertEquals(2,s2.getListenersCount());	
			assertEquals(2,s3.getListenersCount());	
			
		} catch (Exception e) {
			LOG.error("Fehler",e);;
			
			Assert.fail("Exception " + e.getMessage());
		}
	}
}
