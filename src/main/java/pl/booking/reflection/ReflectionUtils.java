package pl.booking.reflection;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Szymon on 4/12/2016.
 */
class ReflectionUtils {
    private static final Package[] packages = Package.getPackages();

    private static Map<String, Class> classCache = new HashMap<>();

    public static Class<?> getClassFor(String className, Class interfaceClass) {
        String classSimpleName = upperFirstLetter(className);
        if (classCache.containsKey(classSimpleName)) {
            return classCache.get(classSimpleName);
        } else {
            Class<?> aClass = findClass(classSimpleName, interfaceClass);
            if (aClass != null) {
                classCache.put(classSimpleName, aClass);
            }
        }
        return classCache.get(classSimpleName);
    }

    private static Class<?> findClass(String className, Class interfaceClass) {
        Class<?> aClass;
        for (final Package p : packages) {
            final String pack = p.getName();
            final String tentative = pack + "." + className;
            try {
                aClass = Class.forName(tentative);
                if (interfaceClass != null) {
                    for (Class<?> iClass : aClass.getInterfaces()) {
                        if (interfaceClass.equals(iClass)) {
                            return aClass;
                        }
                    }
                } else {
                    return aClass;
                }
            } catch (final ClassNotFoundException e) {
                continue;
            }
            break;
        }
        return null;
    }


    public static String upperFirstLetter(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }

    public static String lowerFirstLetter(String string) {
        return string.substring(0, 1).toLowerCase() + string.substring(1);
    }

    public static String replaceAll(String string, String[] strings) {
        for (String s : strings) {
            string = string.replace(s, "");
        }
        return string;
    }
}
