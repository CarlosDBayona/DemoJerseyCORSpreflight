package configuration.jerseyConfiguration;

import com.sun.jersey.core.util.Priority;
import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;
import com.sun.jersey.spi.container.ContainerResponse;
import com.sun.jersey.spi.container.ContainerResponseFilter;
import java.io.IOException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import javax.ws.rs.ext.Provider;

@Provider
public class CORSFilter implements ContainerResponseFilter {

    private String _corsHeaders;

    /**
     * Method for ContainerRequestFilter.
     *
     * @param request
     * @param response
     * @return
     */
    @Override
    public ContainerResponse filter(ContainerRequest request,
            ContainerResponse response) {
        /*response.getHttpHeaders().add("Access-Control-Allow-Origin", "*");
        response.getHttpHeaders().add("Access-Control-Allow-Headers",
                "origin, content-type, accept, authorization, *");
        response.getHttpHeaders().add("Access-Control-Allow-Credentials", "true");
        response.getHttpHeaders().add("Access-Control-Allow-Methods",
                "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        Response.R
        return response;*/
        ResponseBuilder crunchifyResponseBuilder = Response.fromResponse(response.getResponse());

        // *(allow from all servers) OR https://crunchify.com/ OR http://example.com/
        crunchifyResponseBuilder.header("Access-Control-Allow-Origin", "*")
                // As a part of the response to a request, which HTTP methods can be used during the actual request.
                .header("Access-Control-Allow-Methods", "API, CRUNCHIFYGET, GET, POST, PUT, UPDATE, OPTIONS")
                // How long the results of a request can be cached in a result cache.
                .header("Access-Control-Max-Age", "151200")
                // As part of the response to a request, which HTTP headers can be used during the actual request.
                .header("Access-Control-Allow-Headers", "x-requested-with,Content-Type,*");

        String crunchifyRequestHeader = request.getHeaderValue("Access-Control-Request-Headers");

        if (null != crunchifyRequestHeader && !crunchifyRequestHeader.equals(null)) {
            crunchifyResponseBuilder.header("Access-Control-Allow-Headers", crunchifyRequestHeader);
        }

        response.setResponse(crunchifyResponseBuilder.build());
        return response;
    }
}
