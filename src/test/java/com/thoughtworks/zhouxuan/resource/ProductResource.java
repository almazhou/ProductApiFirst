package com.thoughtworks.zhouxuan.resource;

import com.thoughtworks.zhouxuan.json.ProductJson;
import com.thoughtworks.zhouxuan.repository.ProductRepository;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.stream.Collectors;

@Path("/products")
public class ProductResource {
   @Inject
   private ProductRepository productRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ProductJson> getAllProducts(@Context UriInfo uriInfo){

        return productRepository.getAllProducts().stream().map(product -> new ProductJson(product, uriInfo)).collect(Collectors.toList());
    }
}
