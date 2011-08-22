package org.duckapter.performance;

import org.duckapter.annotation.Constructor;

public interface JavaBeanConstructor {
	@Constructor IJavaBean newInstance();
}
