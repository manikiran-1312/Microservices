package com.example.demo.Controller;

import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.Exception.RecordNotFoundException;
import com.example.demo.Model.Employee;
import com.example.demo.Response.EmployeeResponse;
import com.example.demo.Response.EmployeeResponseList;
import com.example.demo.Service.EmployeeService;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	private static final Logger logger = LogManager.getLogger(EmployeeController.class);

	@PostMapping(value = "/addemployee", consumes = { "application/json", "application/xml" }, produces = {
			"application/json", "application/xml" })
	public ResponseEntity<?> addEmployee(@RequestBody Employee employee) {
		try {
			if (employee == null) {

				logger.debug("Employee object should not be Null");

				EmployeeResponse response = new EmployeeResponse();
				response.setMessage("Employee Object Should not Be Null");
				response.setStatusCode("400");
				return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			}

			if (employee.getName() == null || employee.getName().isEmpty() || employee.getSal() == null
					|| employee.getCity() == null || employee.getCity().isEmpty() || employee.getPhone() == null) {

				logger.debug("Employee Fields Should not Be Empty");
				EmployeeResponse response = new EmployeeResponse();

				response.setMessage("Employee Fields Should Not Be Empty");
				response.setStatusCode("422");
				return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			Employee addEmployee = employeeService.addEmployee(employee);

			if (addEmployee == null) {
				logger.info("Failed To Added Employee");
				EmployeeResponse response = new EmployeeResponse();

				response.setMessage("Failed To Added Employee");
				response.setStatusCode("417");
				return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);

			}
			logger.info("Employee Added SuccesFully");
			EmployeeResponse response = new EmployeeResponse();

			response.setMessage("Employee Added Successfully");
			response.setStatusCode("200");
			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (SQLException exception) {
			logger.error("Exception Caught " + exception);
			EmployeeResponse response = new EmployeeResponse();

			response.setMessage("Exception Caught : " + exception);
			response.setStatusCode("409");
			exception.printStackTrace();
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);

		} catch (Exception e) {
			logger.error("Exception Caught " + e);
			EmployeeResponse response = new EmployeeResponse();

			response.setMessage("Exception Caught : " + e);
			response.setStatusCode("409");
			e.printStackTrace();
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}

	}

	@GetMapping(value = "/getbyemployeeid/{employeeid}")

	public ResponseEntity<?> getByEmployeeId(@PathVariable("employeeid") Integer employeeid) {
		try {

			if (employeeid == null) {

				logger.debug("Please pass the employeeid");
				EmployeeResponse response = new EmployeeResponse();

				response.setMessage("Please pass the employeeid");
				response.setStatusCode("422");
				return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			Employee employee = employeeService.getByEmployeeId(employeeid);

			logger.info("Employee Found with This employee id : " + employeeid + " :" + employee);
			EmployeeResponse response = new EmployeeResponse();

			response.setMessage("Employee Present with Employe id : " + employeeid + " Employee Data is :" + employee);
			response.setStatusCode("200");
			return new ResponseEntity<>(employee, HttpStatus.OK);

		} catch (SQLException exception) {
			logger.error("Exception Caught " + exception);
			EmployeeResponse response = new EmployeeResponse();

			response.setMessage("Exception Caught : " + exception);
			response.setStatusCode("409");
			exception.printStackTrace();
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		} catch (RecordNotFoundException e) {

			logger.error("RecordNotFoundException : " + e);
			EmployeeResponse response = new EmployeeResponse();

			response.setMessage("RecordNotFoundException : " + e.getMessage());
			response.setStatusCode("409");
			e.printStackTrace();
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		} catch (Exception e) {

			logger.error("Exception Caught : " + e);
			EmployeeResponse response = new EmployeeResponse();

			response.setMessage("Exception Caught : " + e);
			response.setStatusCode("409");
			e.printStackTrace();
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}

	}

	@PutMapping(value = "/updateemployee", consumes = { "application/json", "application/xml" }, produces = {
			"application/json", "application/xml" })
	public ResponseEntity<?> updateEmployee(@RequestBody Employee employee) {
		try {
			if (employee == null) {
				logger.debug("Employee object should not be Null");
				EmployeeResponse response = new EmployeeResponse();

				response.setMessage("Employee Object Should not Be Null");
				response.setStatusCode("400");
				return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			}

			if (employee.getName() == null || employee.getName().isEmpty() || employee.getSal() == null
					|| employee.getCity() == null || employee.getCity().isEmpty() || employee.getPhone() == null) {

				logger.debug("Employee Fields Should not Be Empty");
				EmployeeResponse response = new EmployeeResponse();

				response.setMessage("Employee Fields Should Not Be Empty");
				response.setStatusCode("422");
				return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			Employee updateEmployee = employeeService.updateEmployee(employee);

			if (updateEmployee == null) {
				logger.info("Failed To Updated Employee");
				EmployeeResponse response = new EmployeeResponse();

				response.setMessage("Failed To Updated Employee");
				response.setStatusCode("417");
				return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);

			}
			logger.info("Employee Updated SuccesFully");
			EmployeeResponse response = new EmployeeResponse();

			response.setMessage("Employee Updated Successfully");
			response.setStatusCode("200");
			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (SQLException exception) {
			logger.error("Exception Caught " + exception);
			EmployeeResponse response = new EmployeeResponse();

			response.setMessage("Exception Caught : " + exception);
			response.setStatusCode("409");
			exception.printStackTrace();
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		
		} catch (Exception e) {
			logger.error("Exception Caught " + e);
			EmployeeResponse response = new EmployeeResponse();

			response.setMessage("Exception Caught : " + e);
			response.setStatusCode("409");
			e.printStackTrace();
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}

	}

	@DeleteMapping(value = "/deletebyemployeeid/{employeeid}")

	public ResponseEntity<?> deleteByEmployeeId(@PathVariable("employeeid") Integer employeeid) {
		try {
			if (employeeid == null) {

				logger.debug("Please pass the employeeid");
				EmployeeResponse response = new EmployeeResponse();

				response.setMessage("Please pass the employeeid");
				response.setStatusCode("422");
				return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			Employee deleteByEmployeeid = employeeService.deleteByEmployeeid(employeeid);

			if (deleteByEmployeeid == null) {

				logger.info("Failed to Delete Employee with employeeid : " + employeeid);
				EmployeeResponse response = new EmployeeResponse();

				response.setMessage("Failed to Delete Employee with employeeid : " + employeeid);
				response.setStatusCode("417");
				return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);

			} else {

				logger.info("Employee Deleted Successfully with employeeid : " + employeeid);
				EmployeeResponse response = new EmployeeResponse();

				response.setMessage("Employee Deleted Successfully with employeeid : " + employeeid);
				response.setStatusCode("200");
				return new ResponseEntity<>(response, HttpStatus.OK);

			}

		} catch (SQLException exception) {
			logger.error("Exception Caught " + exception);
			EmployeeResponse response = new EmployeeResponse();

			response.setMessage("Exception Caught : " + exception);
			response.setStatusCode("409");
			exception.printStackTrace();
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		} catch (RecordNotFoundException e) {

			logger.error("RecordNotFoundException : " + e);
			EmployeeResponse response = new EmployeeResponse();

			response.setMessage("RecordNotFoundException : " + e);
			response.setStatusCode("409");
			e.printStackTrace();
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		} catch (Exception e) {

			logger.error("Exception Caught : " + e);
			EmployeeResponse response = new EmployeeResponse();

			response.setMessage("Exception Caught : " + e);
			response.setStatusCode("409");
			e.printStackTrace();
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
	}

	// Get All Employee Details //
	@GetMapping(path = { "name/{pageNo}/{sizePerPage}/{searchByName}" })
	public ResponseEntity<?> getAllEmployeeInfo(
			@PathVariable(required = false, name = "searchByName") String searchByName,
			@PathVariable("pageNo") Integer pageNo, @PathVariable("sizePerPage") Integer sizePerPage) {
		if (searchByName.equalsIgnoreCase("null")) {
			searchByName = null;
		}
		if (pageNo == null || sizePerPage == null) {
			logger.error("Object is null  in post");
			EmployeeResponse response = new EmployeeResponse();

			response.setMessage("page number and size per page shouldnot null");
			response.setStatusCode("400");

			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		try {
			EmployeeResponseList listOfEmployee = null;

			if (searchByName == null) {
				listOfEmployee = employeeService.getAllEmployeeDetails(pageNo, sizePerPage);
			}

			else {
				listOfEmployee = employeeService.getAllEmployeeDetails(searchByName, pageNo, sizePerPage);
			}

			logger.info("getting list of records" + listOfEmployee);

			return new ResponseEntity<>(listOfEmployee, HttpStatus.OK);
		} catch (Exception e) {

			logger.debug("inside catch block " + e.getMessage());
			EmployeeResponse response = new EmployeeResponse();

			response.setMessage("Exception caught");
			response.setStatusCode("409");
			e.printStackTrace();
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}

	}

	// Get All Employee Details By Salary //
	@GetMapping(path = { "salary/{pageNo}/{sizePerPage}/{searchBySalary}" })
	public ResponseEntity<?> getAllEmployeeInfoBySalary(
			@PathVariable(required = false, name = "searchBySalary") Integer searchBySalary,
			@PathVariable("pageNo") Integer pageNo, @PathVariable("sizePerPage") Integer sizePerPage) {

		if (pageNo == null || sizePerPage == null) {
			logger.error("Object is null  in post");
			EmployeeResponse response = new EmployeeResponse();

			response.setMessage("page number and size per page shouldnot null");
			response.setStatusCode("400");

			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		try {
			EmployeeResponseList listOfEmployee = null;

			if (searchBySalary == 0) {
				listOfEmployee = employeeService.getAllEmployeeDetails(pageNo, sizePerPage);

			}
			
		

			else {
				listOfEmployee = employeeService.getAllEmployeeDetailsBySalary(searchBySalary, pageNo, sizePerPage);

			}

			logger.info("getting list of records" + listOfEmployee);

			return new ResponseEntity<>(listOfEmployee, HttpStatus.OK);
		} catch (Exception e) {

			logger.debug("inside catch block " + e.getMessage());
			EmployeeResponse response = new EmployeeResponse();

			response.setMessage("Exception caught");
			response.setStatusCode("409");
			e.printStackTrace();
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}

	}

	// Get All Employee Details By City//
	@GetMapping(path = { "city/{pageNo}/{sizePerPage}/{searchByCity}" })
	public ResponseEntity<?> getAllEmployeeInfoByCity(
			@PathVariable(required = false, name = "searchByCity") String searchByCity,
			@PathVariable("pageNo") Integer pageNo, @PathVariable("sizePerPage") Integer sizePerPage) {
		if (searchByCity.equalsIgnoreCase("null")) {
			searchByCity = null;
		}
		if (pageNo == null || sizePerPage == null) {
			logger.error("Object is null  in post");
			EmployeeResponse response = new EmployeeResponse();

			response.setMessage("page number and size per page shouldnot null");
			response.setStatusCode("400");

			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		try {
			EmployeeResponseList listOfEmployee = null;

			if (searchByCity == null) {
				listOfEmployee = employeeService.getAllEmployeeDetails(pageNo, sizePerPage);
			}

			else {
				listOfEmployee = employeeService.getAllEmployeeDetailsByCity(searchByCity, pageNo, sizePerPage);
			}

			logger.info("getting list of records" + listOfEmployee);

			return new ResponseEntity<>(listOfEmployee, HttpStatus.OK);
		} catch (Exception e) {

			logger.debug("inside catch block " + e.getMessage());
			EmployeeResponse response = new EmployeeResponse();

			response.setMessage("Exception caught");
			response.setStatusCode("409");
			e.printStackTrace();
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}

	}

	// Get UserProfile details By Phone //
	@GetMapping("employeePhone/{phone}")
	public ResponseEntity<?> getEmployeeDetailsByPhone(@PathVariable("phone") int phone)
			throws RecordNotFoundException {
		try {

			if (phone == 0) {

				logger.error("Employee status is null  in getPhone by Phone");
				EmployeeResponse response = new EmployeeResponse();

				response.setMessage("Phone is null");
				response.setStatusCode("400");

				return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			}

			Employee profile = employeeService.findByPhone(phone);

			if (profile == null) {
				logger.error("Employee status is null  in getPhone by Phone");
				EmployeeResponse response = new EmployeeResponse();

				response.setMessage("profile is null");
				response.setStatusCode("400");

				return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//				throw new RecordNotFoundException("Record is not present");
			}

			return new ResponseEntity<>(profile, HttpStatus.OK);
		} catch (Exception e) {
			logger.debug("inside catch block " + e.getMessage());
			EmployeeResponse response = new EmployeeResponse();  

			response.setMessage("Exception caught");
			response.setStatusCode("409");
			e.printStackTrace();
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);

		}

	}

}
