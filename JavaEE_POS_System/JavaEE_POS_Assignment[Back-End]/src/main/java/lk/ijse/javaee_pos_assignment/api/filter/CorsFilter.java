package lk.ijse.javaee_pos_assignment.api.filter;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(filterName = "filter",urlPatterns = "/*")
public class CorsFilter extends HttpFilter {

static {
    System.out.println("CORSFilter Class object is being initialized");
}

    public CorsFilter() {
        System.out.println("CORSFilter: constructor");
    }

    @Override
    public void init() throws ServletException {
        System.out.println("CORSFilter: init()");
    }
        @Override
        protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
            String origin = req.getHeader("origin");
//            System.out.println(origin);
            if(origin == null){
//                res.sendError(HttpServletResponse.SC_BAD_REQUEST,"CORS Policy Violation");
                res.getWriter().write("Server is Started And No Requests Has Been Received Yet!!");
                return;
            }
            res.addHeader("Access-Control-Allow-Origin", origin);
            res.addHeader("Access-Control-Allow-Headers", "Content-Type");
            res.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTION, HEAD");
            chain.doFilter(req,res);
        }
    }

