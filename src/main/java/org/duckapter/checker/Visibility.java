package org.duckapter.checker;

import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;

import org.duckapter.annotation.Package;
import org.duckapter.annotation.Private;
import org.duckapter.annotation.Protected;
import org.duckapter.annotation.Public;

/**
 * This enumeration declares modes for determining the desired visibility of the
 * target element.
 * 
 * @author Vladimir Orany
 * 
 */
public enum Visibility {
	/**
	 * The visibility of the target element must be at least the same as
	 * declared by the annotation.
	 * 
	 * @author Vladimir Orany
	 * 
	 */
	AT_LEAST {
		@Override
		protected boolean checkPackage(int mod) {
			return !Modifier.isPrivate(mod) && !Modifier.isProtected(mod);
		}

		@Override
		protected boolean checkPrivate(int mod) {
			return true;
		}

		@Override
		protected boolean checkProtected(int mod) {
			return !Modifier.isPrivate(mod);
		}

		@Override
		protected boolean checkPublic(int mod) {
			return Modifier.isPublic(mod);
		}
	},
	/**
	 * The visibility of the target element must be at most the same as declared
	 * by the annotation.
	 * 
	 * @author Vladimir Orany
	 * 
	 */
	AT_MOST {
		@Override
		protected boolean checkPackage(int mod) {
			return !Modifier.isPublic(mod);
		}

		@Override
		protected boolean checkPrivate(int mod) {
			return Modifier.isPrivate(mod);
		}

		@Override
		protected boolean checkProtected(int mod) {
			return Modifier.isProtected(mod) || Modifier.isPrivate(mod);
		}

		@Override
		protected boolean checkPublic(int mod) {
			return true;
		}
	},
	/**
	 * The visibility of the target element must be the same as declared by the
	 * annotation.
	 * 
	 * @author Vladimir Orany
	 * 
	 */
	EXACT {
		@Override
		protected boolean checkPackage(int mod) {
			return !Modifier.isPrivate(mod) && !Modifier.isPublic(mod)
					&& !Modifier.isProtected(mod);
		}

		@Override
		protected boolean checkPrivate(int mod) {
			return Modifier.isPrivate(mod);
		}

		@Override
		protected boolean checkProtected(int mod) {
			return Modifier.isProtected(mod);
		}

		@Override
		protected boolean checkPublic(int mod) {
			return Modifier.isPublic(mod);
		}

	};

	boolean check(Annotation anno, int mod) {
		if (anno == null || anno instanceof Public) {
			return checkPublic(mod);
		}
		if (anno instanceof Package) {
			return checkPackage(mod);
		}
		if (anno instanceof Protected) {
			return checkProtected(mod);
		}
		if (anno instanceof Private) {
			return checkPrivate(mod);
		}
		return false;
	}

	protected abstract boolean checkPrivate(int mod);

	protected abstract boolean checkProtected(int mod);

	protected abstract boolean checkPackage(int mod);

	protected abstract boolean checkPublic(int mod);

}
