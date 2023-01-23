package Tests;

import io.restassured.response.Response;
import listeners.ExtentTestManager;
import org.testng.annotations.Test;
import restAPI.Employee;
import restAPIEndpoints.RestAPIEndpoints;

import java.lang.reflect.Method;

public class ApiTest  {
    RestAPIEndpoints restAPITests = new RestAPIEndpoints();


    @Test
    public void testExample(Method method)  {
        ExtentTestManager.startTest(method.getName(), "Description: Valid api Scenario - load all employees");
        restAPITests.getAllEmployees();

        ExtentTestManager.startTest(method.getName(), "Description: Valid api Scenario - create employee");
        Employee employee = new Employee("golan", 100000, 18, null);
        Response response = restAPITests.createEmployee( employee);

        int id = response.jsonPath().getInt("data.id");
        ExtentTestManager.startTest(method.getName(), "Description: Valid api Scenario - get employee id :"+ id +" that was created earlier");
        restAPITests.getEmployeeAfterCreated(id, employee);
    }
}