package com.thoughtworks.zhouxuan.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class PricingNotFoundExceptionMapper implements ExceptionMapper<PricingNotFoundException> {
    @Override
    public Response toResponse(PricingNotFoundException exception) {
        return Response.status(404).build();
    }
}
