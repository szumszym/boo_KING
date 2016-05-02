package pl.booking.reflection;

import org.springframework.data.jpa.repository.Query;
import pl.booking.db.repo.SearchRepository;

import javax.persistence.EntityManager;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Szymon on 4/12/2016.
 */
class SearchControllerUtils {
    private static final Package[] packages = Package.getPackages();
    private static final String[] methodPrefixes = new String[]{"findAll", "find"};
    private static Map<String, Map<String, Map<String, String>>> searchAPI;
    private static Map<String, Class> classCache = new HashMap<>();

    public static Object executeSearch(EntityManager entityManager, String searchMethod) throws ClassNotFoundException {
        Object body = null;
        for (Method method : SearchRepository.class.getDeclaredMethods()) {
            String name = method.getName();
            String searchName = lowerFirstLetter(replaceAll(name, methodPrefixes));
            boolean isCorrectMethod = searchMethod.equalsIgnoreCase(searchName);
            if (!isCorrectMethod) {
                continue;
            }

            Class aClass = Object.class;
            Type genericReturnType = method.getGenericReturnType();

            if (genericReturnType instanceof ParameterizedType) {
                aClass = (Class) ((ParameterizedType) genericReturnType).getRawType();
            } else if (genericReturnType instanceof Class) {
                aClass = (Class) genericReturnType;
            }

            Query annotation = method.getAnnotation(Query.class);
            String hql = annotation.value();


            javax.persistence.Query query = entityManager.createQuery(hql);
            if (List.class.isAssignableFrom(aClass)) {
                body = query.getResultList();
            } else if (Map.class.isAssignableFrom(aClass)) {
                List<Object[]> resultList = query.getResultList();
                Map map = new HashMap();
                for (Object[] row : resultList) {
                    map.put(row[0], row[1]);
                }
                body = map;
            } else {
                body = query.getSingleResult();
            }
        }
        return body;
    }

    public static Map<String, Map<String, Map<String, String>>> buildSearchHATEOS(String requestURL) {
        if (searchAPI != null) {
            return searchAPI;
        }
        searchAPI = new HashMap<>();
        HashMap<String, Map<String, String>> links = new HashMap<>();
        searchAPI.put("_links", links);

        Method[] declaredMethods = SearchRepository.class.getDeclaredMethods();
        for (Method method : declaredMethods) {
            String methodName = method.getName();

            String path = lowerFirstLetter(replaceAll(methodName, methodPrefixes));

            if (!requestURL.endsWith("/")) {
                requestURL += "/";
            }
            HashMap<String, String> href = new HashMap<>();
            href.put("href", requestURL + path);
            links.put(path.replaceAll("/", ""), href);
        }
        return searchAPI;
    }

    private static Class<?> getClassFor(String className, Class interfaceClass) {
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

    private static String upperFirstLetter(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }

    private static String lowerFirstLetter(String string) {
        return string.substring(0, 1).toLowerCase() + string.substring(1);
    }

    private static String replaceAll(String string, String[] strings) {
        for (String s : strings) {
            string = string.replace(s, "");
        }
        return string;
    }
}
