package pl.booking.reflection;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;

import static pl.booking.reflection.SearchControllerUtils.buildSearchHATEOS;
import static pl.booking.reflection.SearchControllerUtils.executeSearch;

/**
 * Created by Szymon on 4/11/2016.
 */
@Controller
public class SearchController {

    private static final String SEARCH_PATH = "api/search";
    @PersistenceContext
    private EntityManager entityManager;

    @RequestMapping(value = SEARCH_PATH, method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getSearches(HttpServletRequest request) {
        String requestURL = request.getRequestURL().toString();
        return ResponseEntity.status(HttpStatus.OK).body(buildSearchHATEOS(requestURL));
    }

    @RequestMapping(value = SEARCH_PATH + "/{searchMethod}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getSearch(@PathVariable String searchMethod) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(executeSearch(entityManager, searchMethod));
        } catch (ClassNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
        }
    }

}

