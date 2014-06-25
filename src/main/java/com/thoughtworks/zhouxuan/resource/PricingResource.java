package com.thoughtworks.zhouxuan.resource;

import com.thoughtworks.zhouxuan.domain.Pricing;
import com.thoughtworks.zhouxuan.domain.Product;
import com.thoughtworks.zhouxuan.json.PricingJson;
import com.thoughtworks.zhouxuan.repository.ProductRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
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
    @Produces(MediaType.APPLICATION_JSON)
    public List<PricingJson> getAllPricings(){
        List<Pricing> allPricingsOfProduct = productRepository.getAllPricingsOfProduct(product.getId());
        List<PricingJson> pricings = allPricingsOfProduct.stream().map(pricing -> new PricingJson(pricing, product, uriInfo)).collect(Collectors.toList());
        return pricings;
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response createPricing(Form form) {
        Pricing pricing = new Pricing(Double.valueOf(form.asMap().getFirst("amount")));
        productRepository.savePricing(product, pricing);
        String uri = uriInfo.getBaseUri() + "products/" + String.valueOf(product.getId()) + "/pricings/" + String.valueOf(new Pricing(Double.valueOf(form.asMap().getFirst("amount"))).getId());
        return Response.created(URI.create(uri)).build();
    }
}
