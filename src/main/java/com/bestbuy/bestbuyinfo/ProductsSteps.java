package com.bestbuy.bestbuyinfo;

import com.bestbuy.constants.EndPoints;
import com.bestbuy.constants.ProductsPath;
import com.bestbuy.model.ProductPojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.rest.SerenityRest;

import java.util.HashMap;

public class ProductsSteps extends ProductPojo {

    @Step("Creating Product with name: {0}, type: {1}, price: {2}, upc: {3}, shipping: {4}, description: {5}, manufacturer: {6}, model: {7}, url: {8}, image: {9}")
    public ValidatableResponse createProduct(String name, String type, double price, String upc,
                                             double shipping, String description, String manufacturer,
                                             String model, String url, String image
    ){
        ProductPojo productPojo = ProductPojo.getProductPojo(name,type, price,
                upc,shipping,description, manufacturer,model, url, image);

        return SerenityRest.given().log().ifValidationFails()
                .contentType(ContentType.JSON)
                .when()
                .body(productPojo)
                .post()
                .then();
    }

    @Step("Getting the Student information with name : {0}")
    public HashMap<String, Object> getProductInformationByName(String name){
        String s1 = "findAll{it.name == '";
        String s2 = "'}.get(0)";
        String newStr = s1 + name + s2;
        System.out.println(newStr);
        return SerenityRest.given()
                .when()
                .get()
                .then().statusCode(200)
                .extract()
                .path(newStr);
    }

    @Step("Updating student information with productId: {0}, name: {1}, type: {2}, price: {3}, upc: {4}, " +
            "shipping: {5}, description: {6}, manufacturer: {7}, model: {8}, url: {9}, image: {10}")
    public ValidatableResponse updateProduct(int productId, String name, String type, double price, String upc,
                                             double shipping, String description, String manufacturer,
                                             String model, String url, String image){
        ProductPojo productPojo = ProductPojo.getProductPojo(name,type, price,
                upc,shipping,description, manufacturer,model, url, image);

        return SerenityRest.given().log().all()
                .header("Content-Type", "application/json")
                .pathParam("productID", productId)
                .body(productPojo)
                .when()
                .put(EndPoints.UPDATE_PRODUCT_BY_ID)
                .then();
    }

    @Step("Deleting product information with studentId: {0}")
    public ValidatableResponse deleteProduct(int productId){
        return SerenityRest.given().log().all()
                .pathParam("productID", productId)
                .when()
                .delete(EndPoints.DELETE_PRODUCT_BY_ID)
                .then();
    }

    @Step("Getting student information with studentId: {0}")
    public ValidatableResponse getProductById(int productId){
        return SerenityRest.given().log().all()
                .pathParam("productID", productId)
                .when()
                .get(EndPoints.GET_SINGLE_PRODUCT_BY_ID)
                .then();
    }
}
