package cz.muni.fi.pa165.bluebat.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import cz.muni.fi.pa165.bluebat.ApiUris;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/main")
public class MainController {

    /**
     * The main entry point of the REST API
     * Provides access to all the resources with links to resource URIs
     * Can be even extended further so that all the actions on all the resources are available
     *
     * @return resources uris
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final Map<String, String> getResources() {
        Map<String,String> resourcesMap = new HashMap<>();

        resourcesMap.put("reservations_uri", ApiUris.ROOT_URI_RESERVATIONS);

        return Collections.unmodifiableMap(resourcesMap);
    }
}
