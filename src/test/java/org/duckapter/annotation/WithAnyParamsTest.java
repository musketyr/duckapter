package org.duckapter.annotation;

import java.util.Date;

import org.duckapter.DuckTestHelper;
import org.junit.Test;

public class WithAnyParamsTest {
	
	public static interface WAP {
		@WithAnyParams void doIt();
	}
	
	public static class WAPP {
		public void doIt(Date when){}
	}
	
	public static class WAPF {
		public void doItWhen(Date when){}
	}
	
	@Test public void test(){
		DuckTestHelper.assertCanAdaptInstance(WAP.class, WAPP.class, WAPF.class);
	}

}
