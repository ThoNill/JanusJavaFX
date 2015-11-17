package builder.test;

import org.janus.binder.BindWalker;





public class TestBinderWalker extends BindWalker {

	public TestBinderWalker() {
		init();
	}
	
	@Override
	protected void init() {
		add("STRING", "name", "source");
		add("BEAN", "name", "CALL","name");
		add("CALL", "name", "SET","to");
		super.init();
	}

}
