package com.thoughtworks.zhouxuan.resource;

import com.thoughtworks.zhouxuan.domain.Pricing;
import com.thoughtworks.zhouxuan.domain.Product;
import com.thoughtworks.zhouxuan.json.PricingJson;
import com.thoughtworks.zhouxuan.repository.ProductRepository;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.stream.Collectors;

public class PricingResource {
    private ProductRepository productRepository;
    private UriInfo uriInfo;
    private Product product;

    public PricingResource() {
    }

    public PricingResource(Product product, ProductRepository productRepository, UriInfo uriInfo) {
        this.product = product;
        this.productRepository = productRepository;
        this.uriInfo = uriInfo;
    }
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<PricingJson> getAllPricings(@PathParam("id") int id, @Context UriInfo uriInfo){
        List<Pricing> allPricingsOfProduct = productRepository.getAllPricingsOfProduct(product.getId());
        List<PricingJson> pricings = allPricingsOfProduct.stream().map(pricing -> new PricingJson(pricing, product, uriInfo)).collect(Collectors.toList());
        return pricings;
    }
}
