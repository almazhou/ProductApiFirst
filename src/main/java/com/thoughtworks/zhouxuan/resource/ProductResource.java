package com.thoughtworks.zhouxuan.resource;

import com.thoughtworks.zhouxuan.domain.Product;
import com.thoughtworks.zhouxuan.json.ProductJson;
import com.thoughtworks.zhouxuan.repository.ProductRepository;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
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
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response saveProduct(@Context UriInfo uriInfo, Form form) {
        Product newProduct = new Product(form.asMap().getFirst("name"));
        productRepository.saveProduct(newProduct);

        return Response.created(URI.create(uriInfo.getBaseUri() + "/products/" + String.valueOf(newProduct.getId()))).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public ProductJson getProductById(@Context UriInfo uriInfo, @PathParam("id") int id){
        Product productById = productRepository.getProductById(id);
        return new ProductJson(productById,uriInfo);

    }

    @Path("/{id}/pricings")
    public PricingResource getPricingResource(@PathParam("id") int id, @Context UriInfo uriInfo){
        Product productById = productRepository.getProductById(id);
        return new PricingResource(productById,productRepository,uriInfo);
    }
}
