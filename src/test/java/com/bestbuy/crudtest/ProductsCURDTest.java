package com.bestbuy.crudtest;

import com.bestbuy.bestbuyinfo.ProductsSteps;
import com.bestbuy.constants.ProductsPath;
import com.bestbuy.testbase.TestBaseProducts;
import com.bestbuy.utils.TestUtils;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.annotations.Steps;
import net.serenitybdd.annotations.Title;
import org.junit.BeforeClass;
import org.junit.Test;

//@RunWith(SerenityRunner.class)
public class ProductsCURDTest extends TestBaseProducts {
    static int productId;
    static int id;
    static String name = TestUtils.generateName();
    static String type = "HardGood" + TestUtils.getRandomValue();
    static Double price = 5.49;
    static String upc = TestUtils.getRandomValue();
    static Double shipping = 4.99;
    static String description = "Compatible with select electronic devices; AAA size; DURALOCK Power Preserve technology; 4-pack";
    static String manufacturer = "Duracell";
    static String model = "MN2400B4Z";
    static String url = "http://www.bestbuy.com/site/duracell-aaa-batteries-4-pack/43900.p?id=1051384074145&skuId=43900&cmp=RMXCC";
    static String image = "http://img.bbystatic.com/BestBuy_US/images/products/4390/43900_sa.jpg";

    @Steps
    ProductsSteps productSteps;

    @BeforeClass
    public static void inIt() {
        RestAssured.basePath = ProductsPath.PRODUCT;
    }

    @Title("This will create a new product")
    @Test
    public void test001(){
        ValidatableResponse response = productSteps.createProduct(name,type,
                price,upc,shipping,description,manufacturer,model,url,image);
        response.statusCode(201);
        id = response.log().all().extract().path("id");
    }

    @Title("Verify if the product was added to the application")
    @Test
    public void test002(){
        ValidatableResponse response = productSteps.getProductById(id);
        response.statusCode(200);
        //Assert.assertThat(productMap, hasValue(name));
        //productId = (int) productMap.get("id");
    }

    @Title("Update the user information and verify the updated information")
    @Test
    public void test003(){
        name = name + "_updated";
        productSteps.updateProduct(id,name,type,
                price,upc,shipping,description,manufacturer,model,url,image).statusCode(200);
//        HashMap<String , Object> productMap = productSteps.getProductInformationByName(name);
//        Assert.assertThat(productMap, hasValue(name));

    }

    @Title("Delete the student and verify if the student is deleted")
    @Test
    public void test004(){
        productSteps.deleteProduct(id).statusCode(200);
        productSteps.getProductById(id).statusCode(404);
    }
}
