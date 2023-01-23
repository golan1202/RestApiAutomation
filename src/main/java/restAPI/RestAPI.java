package restAPI;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import static io.restassured.RestAssured.given;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RestAPI {
    RequestSpecification reqSpec;
    Response response;
    ObjectMapper objectMapper = new ObjectMapper();

    public RestAPI(String baseUrl) {
         RestAssured.baseURI = baseUrl;
        this.reqSpec = given();
    }

    public Response  get(String endPoint) {
        response = reqSpec.contentType(ContentType.JSON).get(endPoint).then().extract().response();
        return  response;
    }

    public Response  post(String endPoint, Object body)  {
            response = reqSpec.header("Content-type", "application/json"). and().body(body).when().post(endPoint).
                then().extract().response();
        return  response;
}
    // check response validation
    public  void validateStatusCode( int statusCode) {
        Assert.assertEquals( response.statusCode(), statusCode);
    }

    public void validateEmployeeBody(Employee employee) {
        Assert.assertEquals(response.jsonPath().getString("data.employee_name"), employee.getName());
        Assert.assertEquals(response.jsonPath().getInt("data.employee_salary"), employee.getSalary());
        Assert.assertEquals(response.jsonPath().getInt("data.employee_age"),employee.getAge() );
        Assert.assertEquals(response.jsonPath().getString("data.profile_image"), employee.getImage());
    }
    public void validateCreatedEmployeeBody(Employee employee) {
        Assert.assertEquals(response.jsonPath().getString("data.name"), employee.getName());
        Assert.assertEquals(response.jsonPath().getInt("data.salary"), employee.getSalary());
        Assert.assertEquals(response.jsonPath().getInt("data.age"),employee.getAge() );
        Assert.assertEquals(response.jsonPath().getString("data.image"), employee.getImage());
    }

    public String parseObject(Object object)  {
        String json = null;
        try {
            json = objectMapper.writeValueAsString(object);
        }catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
        return json;
    }


}
