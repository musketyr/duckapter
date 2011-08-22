package org.duckapter.annotation;

import static org.duckapter.DuckTestHelper.assertCanAdaptInstance;

import org.junit.Test;

public class SynchronizedTest {

	public static interface WithSyncMethodInterface {
		@Synchronized
		void doIt();
	}

	public static class WithSyncMethodClass {
		public synchronized void doIt() {
		}
	}

	public static class WithoutSyncMethodClass {
		public void doIt() {
		}
	}

	@Test
	public void testSync() throws Exception {
		assertCanAdaptInstance(WithSyncMethodInterface.class,
				WithSyncMethodClass.class, WithoutSyncMethodClass.class);
	}

}
