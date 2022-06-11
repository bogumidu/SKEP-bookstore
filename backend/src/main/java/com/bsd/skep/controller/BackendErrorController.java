package com.bsd.skep.controller;

import com.bsd.skep.model.ApiResponse;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController()
@RequestMapping("/api")
public class BackendErrorController implements ErrorController {

    private final ErrorAttributes errorAttributes;

    public BackendErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @RequestMapping(value = "/error")
    public ApiResponse<String> error(HttpServletRequest request) {
        WebRequest requestAttributes = new ServletWebRequest(request);
        Map<String, Object> errorAttributes =
                this.errorAttributes.getErrorAttributes(requestAttributes, ErrorAttributeOptions
                        .of(ErrorAttributeOptions.Include.BINDING_ERRORS, ErrorAttributeOptions.Include.MESSAGE, ErrorAttributeOptions.Include.STACK_TRACE, ErrorAttributeOptions.Include.EXCEPTION));
        String simpleMessage = (String) errorAttributes.get("error");
        String message = (String) errorAttributes.get("message");
        String exception = (String) errorAttributes.get("exception");
        return new ApiResponse<>(false, ResponseStatusException.class.getName().equals(exception) ? message : simpleMessage);
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}