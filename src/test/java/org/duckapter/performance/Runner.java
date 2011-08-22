package org.duckapter.performance;

import java.lang.reflect.Method;
import java.util.Random;
import java.util.concurrent.Callable;

import org.duckapter.Duck;


public class Runner {
    private static final int THREADS = 1000;
    private static final int ITERATIONS = 1000;

    public static void main(String[] args) throws Exception {
        testConcurrent(NORMAL_ACCESS_TASK, "normal access WARMUP");
        testConcurrent(NORMAL_ACCESS_TASK, "normal access");
        testConcurrent(REFLECTION_ACCESS_TASK, "ref. access WARMUP");
        testConcurrent(REFLECTION_ACCESS_TASK, "ref. access");
        testConcurrent(CACHED_CLASS_REFLECTION_ACCESS_TASK, "cached class access WARMUP");
        testConcurrent(CACHED_CLASS_REFLECTION_ACCESS_TASK, "cached class access");
        testConcurrent(CACHED_CLASS_AND_METHODS_REFLECTION_ACCESS_TASK, "cached class and method access WARMUP");
        testConcurrent(CACHED_CLASS_AND_METHODS_REFLECTION_ACCESS_TASK, "cached class and method access");
        testConcurrent(CACHED_CLASS_AND_METHODS_AND_INSTANCE_REFLECTION_ACCESS_TASK, "cached class, method and instance access WARMUP");
        testConcurrent(CACHED_CLASS_AND_METHODS_AND_INSTANCE_REFLECTION_ACCESS_TASK, "cached class, method and instance access");
        testConcurrent(CACHED_CLASS_AND_DUCKAPTER_REFLECTION_ACCESS_TASK, "cached class w/Duckapter access WARMUP");
        testConcurrent(CACHED_CLASS_AND_DUCKAPTER_REFLECTION_ACCESS_TASK, "cached class w/Duckapter access");
    }

    private static void testConcurrent(final Callable<Integer> task, final String name) throws Exception {
        long startTime = System.currentTimeMillis();
    	Integer result = 0;
        for (int i = 0; i < THREADS; i++) {
			result = task.call();
		}
    	// Omitted for brevity, but this method starts <THREADS> threads
        // which concurrently access the object during <ITERATIONS> iterations.
        long duration = System.currentTimeMillis() - startTime;
        System.out.println(name + ": duration=" + duration + ", result=" + result);
    }

    private static Callable<Integer> NORMAL_ACCESS_TASK = new Callable<Integer>(){

    	public Integer call() throws Exception {
            int counter = 31;
            for (int j = 0; j < ITERATIONS; j++) {

// ################# Instantiate and invoke the bean START #################

                final JavaBean bean = new JavaBean();
                bean.setValue(j);
                final int value = bean.getValue();

// ################## Instantiate and invoke the bean END ##################

                counter = 37 * counter + value;
            }

            return counter;
        }
    };
    

    private static Callable<Integer> REFLECTION_ACCESS_TASK = new Callable<Integer>() {
        public Integer call() throws Exception {
            int counter = 31;
            for (int i = 0; i < ITERATIONS; i++) {
                final Class<JavaBean> clazz = (Class<JavaBean>) Class.forName("org.duckapter.performance.JavaBean");
                JavaBean bean = clazz.newInstance();
                clazz.getMethod("setValue", Integer.TYPE).invoke(bean, i);
                final int value = (Integer) clazz.getMethod("getValue").invoke(bean);
                counter = 31 * counter + value;
            }

            return counter;
        }
    };
    

    private static Callable<Integer> CACHED_CLASS_REFLECTION_ACCESS_TASK = new Callable<Integer>() {
        private final Class<JavaBean> classCache = loadClass();

        private Class<JavaBean> loadClass() {
            try {
                return (Class<JavaBean>) Class.forName("org.duckapter.performance.JavaBean");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Cannot initialize JavaBean", e);
            }
        }

        public Integer call() throws Exception {
            int counter = 31;
            for (int i = 0; i < ITERATIONS; i++) {
                JavaBean bean = classCache.newInstance();
                classCache.getMethod("setValue", Integer.TYPE).invoke(bean, i);
                final int value = (Integer) classCache.getMethod("getValue").invoke(bean);
                counter = 37 * counter + value;
            }

            return counter;
        }
    };
    
    private static Callable<Integer> CACHED_CLASS_AND_DUCKAPTER_REFLECTION_ACCESS_TASK = new Callable<Integer>() {
        private final Class<JavaBean> classCache = loadClass();

        private Class<JavaBean> loadClass() {
            try {
                return (Class<JavaBean>) Class.forName("org.duckapter.performance.JavaBean");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Cannot initialize JavaBean", e);
            }
        }

        public Integer call() throws Exception {
            int counter = 31;
            for (int i = 0; i < ITERATIONS; i++) {
                IJavaBean bean = Duck.type(classCache, JavaBeanConstructor.class).newInstance();
                bean.setValue(i);
                final int value = bean.getValue();
                counter = 37 * counter + value;
            }

            return counter;
        }
    };


    private static Callable<Integer> CACHED_CLASS_AND_METHODS_REFLECTION_ACCESS_TASK = new Callable<Integer>() {
        private final Class<JavaBean> classCache = loadClass();
        private final Method getterCache = loadGetter();
        private final Method setterCache = loadSetter();

        private Method loadGetter() {
            try {
                return classCache.getMethod("getValue");
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("Cannot get getter method handle", e);
            }
        }

        private Method loadSetter() {
            try {
                return classCache.getMethod("setValue", Integer.TYPE);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("Cannot get setter method handle", e);
            }
        }

        private Class<JavaBean> loadClass() {
            try {
                return (Class<JavaBean>) Class.forName("org.duckapter.performance.JavaBean");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Cannot initialize JavaBean", e);
            }
        }

        public Integer call() throws Exception {
            int counter = 31;
            for (int i = 0; i < ITERATIONS; i++) {
                JavaBean bean = classCache.newInstance();
                setterCache.invoke(bean, i);
                final int value = (Integer) getterCache.invoke(bean);
                counter = 37 * counter + value;
            }

            return counter;
        }
    };
    

    private static Callable<Integer> CACHED_CLASS_AND_METHODS_AND_INSTANCE_REFLECTION_ACCESS_TASK = new Callable<Integer>() {
        private final Class<JavaBean> classCache = loadClass();
        private final Method getterCache = loadGetter();
        private final Method setterCache = loadSetter();
        private final JavaBean instanceCache = loadInstance();

        private JavaBean loadInstance() {
            try {
                return classCache.newInstance();
            } catch (Exception e) {
                throw new RuntimeException("Cannot get instance", e);
            }
        }

        private Method loadGetter() {
            try {
                return classCache.getMethod("getValue");
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("Cannot get getter method handle", e);
            }
        }

        private Method loadSetter() {
            try {
                return classCache.getMethod("setValue", Integer.TYPE);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("Cannot get setter method handle", e);
            }
        }

        private Class<JavaBean> loadClass() {
            try {
                return (Class<JavaBean>) Class.forName("org.duckapter.performance.JavaBean");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Cannot initialize JavaBean", e);
            }
        }

        public Integer call() throws Exception {
            int counter = 31;
            for (int i = 0; i < ITERATIONS; i++) {
                setterCache.invoke(instanceCache, i);
                final int value = (Integer) getterCache.invoke(instanceCache);
                counter = 37 * counter + value;
            }

            return counter;
        }
    };



}

