package restAPIEndpoints;

import com.relevantcodes.extentreports.LogStatus;
import io.restassured.response.Response;
import listeners.ExtentTestManager;
import listeners.Retry;
import restAPI.Employee;
import restAPI.RestAPI;

public class RestAPIEndpoints extends RestAPI {
    private static final String  baseURL = "https://dummy.restapiexample.com/api/v1/";
    private static final String  employeesEndPoint = "employees";
    private static final String  employeeEndPoint = "employee/";
    private static final String  createEndPoint = "create";
    Retry retry;

    public RestAPIEndpoints() {
        super(baseURL);

    }

    public void getAllEmployees() {
        get(employeesEndPoint);
         try {
             validateStatusCode(200);
             ExtentTestManager.getTest().log(LogStatus.INFO, "Asserting response code equals 200");
         }  catch(AssertionError e){
             ExtentTestManager.getTest().log(LogStatus.FAIL,"Assertion Failure: " +e.getMessage());
         }
    }


    public Response createEmployee(Employee employee) {
        String json  = parseObject(employee);
        Response response = post(createEndPoint, json);
        try {
            validateStatusCode(200);
            ExtentTestManager.getTest().log(LogStatus.INFO, "Asserting response code equals 200");
            validateCreatedEmployeeBody(employee);
            ExtentTestManager.getTest().log(LogStatus.INFO, "Asserting response body of the employee");
        }  catch(AssertionError e){
            ExtentTestManager.getTest().log(LogStatus.FAIL,"Assertion Failure: " +e.getMessage());
        }
        return response;
    }


    public void getEmployeeAfterCreated(int id, Employee employee) {
        get(employeeEndPoint + id);
        try {
            validateStatusCode(200);
            ExtentTestManager.getTest().log(LogStatus.INFO, "Asserting response code equals 200");
            validateEmployeeBody(employee);
            ExtentTestManager.getTest().log(LogStatus.INFO, "Asserting response body");
        }  catch(AssertionError e){
            ExtentTestManager.getTest().log(LogStatus.FAIL,"Assertion Failure: " +e.getMessage());
        }
    }

}
