package pl.booking.reflection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import static pl.booking.reflection.ReflectionUtils.*;

/**
 * Created by Szymon on 4/11/2016.
 */
@Controller
public class SearchController {

    private static final String REST_ROOT_PATH = "api/";
    private static final String SEARCH_PATH = "search/";
    private static final String[] methodPrefixes = new String[]{"findAll", "find"};
    private static final String SEARCH_REPOSITORY_POSTFIX = "Repository";
    @PersistenceContext
    private EntityManager entityManager;

    @RequestMapping(value = REST_ROOT_PATH + SEARCH_PATH, method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getSearchRepositories(HttpServletRequest request) {
        String requestURL = request.getRequestURL().toString();
        return ResponseEntity.status(HttpStatus.OK).body(new HashMap<>());
    }

    @RequestMapping(value = REST_ROOT_PATH + SEARCH_PATH + "/{searchRepository}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getSearches(@PathVariable String searchRepository, HttpServletRequest request) {
        String requestURL = request.getRequestURL().toString();
        return ResponseEntity.status(HttpStatus.OK).body(buildSearchAPI(searchRepository, requestURL));
    }

    @RequestMapping(value = REST_ROOT_PATH + SEARCH_PATH + "/{searchRepository}/{searchMethod}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getSearch(@PathVariable String searchRepository, @PathVariable String searchMethod) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(executeSearch(searchRepository, searchMethod));
        } catch (ClassNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
        }
    }

    private Object executeSearch(@PathVariable String searchRepository, @PathVariable String searchMethod) throws ClassNotFoundException {
        Object body = null;
        Class<?> repository = getClassFor(searchRepository + SEARCH_REPOSITORY_POSTFIX, CustomRepository.class);
        if (repository != null) {
            for (Method method : repository.getDeclaredMethods()) {
                String name = method.getName();
                Class aClass = null;
                Type genericReturnType = method.getGenericReturnType();

                boolean isIterable = false;
                if (genericReturnType instanceof ParameterizedType) {
                    aClass = (Class) ((ParameterizedType) genericReturnType).getActualTypeArguments()[0];
                    isIterable = true;
                } else if (genericReturnType instanceof Class) {
                    aClass = (Class) genericReturnType;
                    isIterable = false;
                }

                Query annotation = method.getAnnotation(Query.class);
                String hql = annotation.value();
                String searchName = lowerFirstLetter(replaceAll(name, methodPrefixes));

                boolean isCorrectMethod = searchMethod.equalsIgnoreCase(searchName);

                if (isCorrectMethod) {
                    TypedQuery query = entityManager.createQuery(hql, aClass);
                    body = isIterable ? query.getResultList() : query.getSingleResult();
                }
            }
        } else {
            throw new ClassNotFoundException(searchRepository + SEARCH_REPOSITORY_POSTFIX + " was not found.");
        }
        return body;
    }

    private Map<String, Map<String, Map<String, String>>> buildSearchAPI(String searchRepository, String requestURL) {
        Map<String, Map<String, Map<String, String>>> map = new HashMap<>();
        Class<?> repository = getClassFor(searchRepository + SEARCH_REPOSITORY_POSTFIX, CustomRepository.class);

        if (repository != null) {
            HashMap<String, Map<String, String>> links = new HashMap<>();
            map.put("_links", links);

            Method[] declaredMethods = repository.getDeclaredMethods();
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
        }
        return map;
    }
}

