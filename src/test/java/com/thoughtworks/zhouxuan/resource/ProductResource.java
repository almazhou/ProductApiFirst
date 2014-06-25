package com.thoughtworks.zhouxuan.resource;

import com.thoughtworks.zhouxuan.domain.Product;
import com.thoughtworks.zhouxuan.json.ProductInputJson;
import com.thoughtworks.zhouxuan.json.ProductJson;
import com.thoughtworks.zhouxuan.repository.ProductRepository;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
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

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveProduct(@Context UriInfo uriInfo, ProductInputJson productInputJson) {
        int id = productRepository.saveProduct(productInputJson);

        return Response.created(URI.create(uriInfo.getBaseUri()+"/products/"+String.valueOf(id))).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public ProductJson getProductById(@Context UriInfo uriInfo, @PathParam("id") int id){
        Product productById = productRepository.getProductById(id);
        return new ProductJson(productById,uriInfo);

    }
}
