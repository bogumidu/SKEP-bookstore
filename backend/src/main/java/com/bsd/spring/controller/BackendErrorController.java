package com.bsd.spring.controller;

import com.bsd.spring.model.ErrorResponse;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

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
    public ErrorResponse error(HttpServletRequest request) {
        WebRequest requestAttributes = new ServletWebRequest(request);
        Map<String, Object> errorAttributes =
                this.errorAttributes.getErrorAttributes(requestAttributes, ErrorAttributeOptions
                        .defaults());
        return new ErrorResponse((String) errorAttributes.get("error"));
    }

    @Override
    public String getErrorPath() {
        return "/api/error";
    }

}