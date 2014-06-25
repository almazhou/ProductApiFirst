package com.thoughtworks.zhouxuan.resource;

import com.thoughtworks.zhouxuan.domain.Product;
import com.thoughtworks.zhouxuan.exception.ProductNotFoundException;
import com.thoughtworks.zhouxuan.exception.ProductNotFoundExceptionMapper;
import com.thoughtworks.zhouxuan.repository.ProductRepository;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.junit.runner.RunWith;
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductResourceTest extends JerseyTest {
    @Mock
    ProductRepository mockProductRepository;
    @Override
    protected Application configure() {
        AbstractBinder binder = new AbstractBinder() {
            @Override
            protected void configure() {
                bind(mockProductRepository).to(ProductRepository.class);
            }
        };

        return new ResourceConfig().register(binder).register(ProductResource.class).register(ProductNotFoundExceptionMapper.class);
    }

    @Test
    public void should_return_200_when_get_all_products_success() throws Exception {
        when(mockProductRepository.getAllProducts()).thenReturn(Arrays.asList(new Product("product1"),new Product("product2")));

        Response response = target("/products").request().get();

        assertThat(response.getStatus(),is(200));

        List list = response.readEntity(List.class);

        Map product1 = (Map) list.get(0);
        Map product2 = (Map) list.get(1);

        assertThat(product1.get("name"),is("product1"));
        assertThat(product2.get("name"),is("product2"));
    }

    @Test
    public void should_return_404_when_products_not_found_exception_throw() throws Exception {
        when(mockProductRepository.getAllProducts()).thenThrow(ProductNotFoundException.class);

        Response response = target("/products").request().get();

        assertThat(response.getStatus(),is(404));

    }

    @Test
    public void should_return_201_when_post_all_products_successfull() throws Exception {
        when(mockProductRepository.saveProduct(anyObject())).thenReturn(1);
        Map<String, String> input = new HashMap<>();
        input.put("name","product1");

        Response response = target("/products").request().post(Entity.entity(input, MediaType.APPLICATION_JSON));

        assertThat(response.getStatus(),is(201));

        assertTrue(response.getHeaderString("location").contains("/products/1"));

    }


}
