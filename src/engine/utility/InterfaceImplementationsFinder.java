package engine.utility;

import engine.Runtime;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Set;
import java.util.HashSet;

public class InterfaceImplementationsFinder {

    public static Set<Class<? extends Runtime>> findAllImplementations() {
        Set<Class<? extends Runtime>> implementations = new HashSet<>();
        try {
            Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources("");
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                File dir = new File(url.getFile());
                if (dir.exists()) {
                    findImplementations(dir, "", implementations);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return implementations;
    }

    private static void findImplementations(File dir, String packageName, Set<Class<? extends Runtime>> implementations) {
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                findImplementations(file, packageName + file.getName() + ".", implementations);
            } else if (file.getName().endsWith(".class")) {
                String className = packageName + file.getName().substring(0, file.getName().length() - 6);
                try {
                    Class<?> clazz = Class.forName(className);
                    if (Runtime.class.isAssignableFrom(clazz) && !clazz.isInterface()) {
                        implementations.add((Class<? extends Runtime>) clazz);
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
