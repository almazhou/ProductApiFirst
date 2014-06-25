package com.thoughtworks.zhouxuan.resource;

import com.thoughtworks.zhouxuan.domain.Pricing;
import com.thoughtworks.zhouxuan.domain.PricingBuilder;
import com.thoughtworks.zhouxuan.domain.Product;
import com.thoughtworks.zhouxuan.domain.ProductBuilder;
import com.thoughtworks.zhouxuan.exception.PricingNotFoundException;
import com.thoughtworks.zhouxuan.exception.PricingNotFoundExceptionMapper;
import com.thoughtworks.zhouxuan.exception.ProductNotFoundException;
import com.thoughtworks.zhouxuan.exception.ProductNotFoundExceptionMapper;
import com.thoughtworks.zhouxuan.repository.ProductRepository;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.hamcrest.core.Is;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductResourceTest extends JerseyTest {
    @Mock
    ProductRepository mockProductRepository;
    private Product  product11 = new ProductBuilder().setId(1).setName("product1").build();
    ;

    @Override
    protected Application configure() {
        AbstractBinder binder = new AbstractBinder() {
            @Override
            protected void configure() {
                bind(mockProductRepository).to(ProductRepository.class);
            }
        };

        return new ResourceConfig().register(binder).register(ProductResource.class).register(ProductNotFoundExceptionMapper.class).register(PricingNotFoundExceptionMapper.class).register(PricingResource.class);
    }



    @Test
    public void should_return_200_when_get_all_products_success() throws Exception {
        Product product11 = new ProductBuilder().setId(1).setName("product1").build();
        Product product21 = new ProductBuilder().setId(2).setName("product2").build();
        when(mockProductRepository.getAllProducts()).thenReturn(Arrays.asList(product11, product21));

        Response response = target("/products").request().get();

        assertThat(response.getStatus(), is(200));

        List list = response.readEntity(List.class);

        Map product1 = (Map) list.get(0);
        Map product2 = (Map) list.get(1);

        assertThat(product1.get("name"), Is.<Object>is("product1"));
        assertThat(product2.get("name"), Is.<Object>is("product2"));
        assertTrue(product1.get("uri").toString().contains("/products/1"));
        assertTrue(product2.get("uri").toString().contains("/products/2"));
    }

    @Test
    public void should_return_404_when_products_not_found_exception_throw() throws Exception {
        when(mockProductRepository.getAllProducts()).thenThrow(ProductNotFoundException.class);

        Response response = target("/products").request().get();

        assertThat(response.getStatus(), is(404));

    }

    @Test
    public void should_return_201_when_post_all_products_successfull() throws Exception {
        when(mockProductRepository.saveProduct(Matchers.<com.thoughtworks.zhouxuan.json.ProductInputJson>anyObject())).thenReturn(1);
        Map<String, String> input = new HashMap();
        input.put("name", "product1");

        Response response = target("/products").request().post(Entity.entity(input, MediaType.APPLICATION_JSON));

        assertThat(response.getStatus(), is(201));

        assertTrue(response.getHeaderString("location").contains("/products/1"));

    }

    @Test
    public void should_get_200_when_get_one_product() throws Exception {
        when(mockProductRepository.getProductById(1)).thenReturn(product11);

        Response response = target("/products/1").request().get();

        assertThat(response.getStatus(), is(200));

        Map list = response.readEntity(Map.class);


        assertThat(list.get("name"), Is.<Object>is("product1"));
        assertTrue(list.get("uri").toString().contains("/products/1"));

    }

    @Test
    public void should_get_404_when_specific_product_not_fond() throws Exception {
        when(mockProductRepository.getProductById(1)).thenThrow(ProductNotFoundException.class);

        Response response = target("/products/1").request().get();

        assertThat(response.getStatus(), is(404));

    }

    @Test
    public void should_return_200_when_get_all_pricings_of_one_product() throws Exception {
        when(mockProductRepository.getProductById(1)).thenReturn(product11);
        Pricing pricing = new PricingBuilder().setId(1).setAmount(44.00).setProductId(1).build();
        when(mockProductRepository.getAllPricingsOfProduct(1)).thenReturn(Arrays.asList(pricing));

        Response response = target("/products/1/pricings").request().get();

        assertThat(response.getStatus(), is(200));

        List list = response.readEntity(List.class);

        Map price = (Map) list.get(0);

        assertThat(price.get("amount"), Is.<Object>is(44.00));
        assertThat(price.get("productId"), Is.<Object>is(1));
        assertThat(((String) price.get("uri")).contains("/products/1/pricings/1"), is(true));
    }

    @Test
    public void should_return_404_when_price_not_found() throws Exception {
        when(mockProductRepository.getProductById(1)).thenReturn(product11);
        when(mockProductRepository.getAllPricingsOfProduct(1)).thenThrow(PricingNotFoundException.class);

        Response response = target("/products/1/pricings").request().get();

        assertThat(response.getStatus(), is(404));

    }

    @Test
    public void should_return_201_when_post_one_pricing() throws Exception {
        Map<String, String> input = new HashMap();
        input.put("amount", "20.00");
        when(mockProductRepository.getProductById(1)).thenReturn(product11);
        when(mockProductRepository.savePricing(anyObject(),anyObject())).thenReturn(1);

        Response response = target("/products/1/pricings").request().post(Entity.entity(input, MediaType.APPLICATION_JSON));

        assertThat(response.getStatus(), is(201));

        assertTrue(response.getHeaderString("location").contains("/products/1/pricings/1"));

    }
}

