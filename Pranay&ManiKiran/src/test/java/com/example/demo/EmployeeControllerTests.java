package com.example.demo;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demo.Controller.EmployeeController;
import com.example.demo.Exception.RecordNotFoundException;
import com.example.demo.Model.Employee;
import com.example.demo.Response.EmployeeResponseList;
import com.example.demo.Service.EmployeeService;

/*@SpringBootTest
*/
class EmployeeControllerTests {

	@InjectMocks
	private EmployeeController employeeController;

	@Mock
	private EmployeeService employeeService;

	@BeforeEach
	public void init() {

		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void createEmployee_ok() throws SQLException {

		Employee employee = new Employee();
		employee.setId(10);
		employee.setName("pranay");
		employee.setCity("hyderabad");
		employee.setPhone(9949467);
		employee.setSal(10000);

		when(employeeService.addEmployee(employee)).thenReturn(employee);

		ResponseEntity<?> addEmployee = employeeController.addEmployee(employee);

		assertEquals(addEmployee.getStatusCode(), HttpStatus.OK);

	}

	@Test
	public void createEmployee_ok_notcreated() throws SQLException {

		Employee employee = new Employee();
		employee.setId(10);
		employee.setName("pranay");
		employee.setCity("hyderabad");
		employee.setPhone(9949467);
		employee.setSal(10000);

		when(employeeService.addEmployee(employee)).thenReturn(null);

		ResponseEntity<?> createEmployee = employeeController.addEmployee(employee);

		assertEquals(createEmployee.getStatusCode(), HttpStatus.EXPECTATION_FAILED);

	}

	@Test
	public void createEmployee_validations() throws Exception {

		Employee employee = null;

		ResponseEntity<?> addEmployee = employeeController.addEmployee(employee);

		assertEquals(addEmployee.getStatusCode(), HttpStatus.BAD_REQUEST);
	}

	@Test
	public void createEmployee_validationOfFields() throws Exception {

		Employee employee = new Employee();
		employee.setName("");
		employee.setCity("");
		employee.setPhone(null);
		employee.setSal(null);

		ResponseEntity<?> addEmployee = employeeController.addEmployee(employee);

		assertEquals(addEmployee.getStatusCode(), HttpStatus.UNPROCESSABLE_ENTITY);

	}

	@Test
	public void createEmployee_sqlException() throws SQLException {

		Employee employee = new Employee();
		employee.setName("Mani Kiran");
		employee.setCity("Hyderabad");
		employee.setPhone(9949467);
		employee.setSal(10000);

		when(employeeService.addEmployee(employee)).thenThrow(SQLException.class);

		ResponseEntity<?> addEmployee = employeeController.addEmployee(employee);

		assertEquals(addEmployee.getStatusCode(), HttpStatus.CONFLICT);

	}

	@Test
	public void createEmployee_Exception() throws Exception {

		Employee employee = new Employee();

		employee.setName("Mani Kiran");
		employee.setCity("Hyderabad");
		employee.setPhone(9949467);
		employee.setSal(10000);

		when(employeeService.addEmployee(employee)).thenThrow(new RuntimeException());

		ResponseEntity<?> addEmployee = employeeController.addEmployee(employee);

		assertEquals(addEmployee.getStatusCode(), HttpStatus.CONFLICT);
	}

	@Test
	public void getEmployeeById_Id_null_check() {

		ResponseEntity<?> employeeId = employeeController.getByEmployeeId(null);

		assertEquals(employeeId.getStatusCode(), HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@Test
	public void getEmployeeById_ok() throws SQLException, RecordNotFoundException {

		Employee employee = new Employee();

		employee.setId(10);
		employee.setName("Mani Kiran");
		employee.setCity("Hyderabad");
		employee.setPhone(99484822);
		employee.setSal(10000);

		when(employeeService.getByEmployeeId(10)).thenReturn(employee);

		ResponseEntity<?> byEmployeeId = employeeController.getByEmployeeId(10);

		assertEquals(byEmployeeId.getStatusCode(), HttpStatus.OK);

	}

	@Test
	public void getEmployeeById_RecordNotFoundException() throws Exception {

		when(employeeService.getByEmployeeId(10)).thenThrow(RecordNotFoundException.class);

		ResponseEntity<?> byEmployeeId = employeeController.getByEmployeeId(10);

		assertEquals(byEmployeeId.getStatusCode(), HttpStatus.CONFLICT);
	}

	@Test
	public void getEmployeeById_SqlException_check() throws Exception {

		when(employeeService.getByEmployeeId(10)).thenThrow(SQLException.class);

		ResponseEntity<?> byEmployeeId = employeeController.getByEmployeeId(10);

		assertEquals(byEmployeeId.getStatusCode(), HttpStatus.CONFLICT);

	}

	@Test
	public void getEmployeeId_Exception_check() throws Exception {

		when(employeeService.getByEmployeeId(10)).thenThrow(RuntimeException.class);

		ResponseEntity<?> byEmployeeId = employeeController.getByEmployeeId(10);

		assertEquals(byEmployeeId.getStatusCode(), HttpStatus.CONFLICT);
	}

	@Test
	public void deleteByEmployeeById_Id_null_checker() throws Exception {

		ResponseEntity<?> deleteByEmployeeId = employeeController.deleteByEmployeeId(null);

		assertEquals(deleteByEmployeeId.getStatusCode(), HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@Test
	public void deleteEmployeeById_ok() throws Exception {

		Employee employee = new Employee();

		employee.setId(10);
		employee.setName("Mani Kiran");
		employee.setCity("Hyderabad");
		employee.setPhone(99484822);
		employee.setSal(10000);

		when(employeeService.deleteByEmployeeid(10)).thenReturn(employee);

		ResponseEntity<?> deleteByEmployeeId = employeeController.deleteByEmployeeId(10);

		assertEquals(deleteByEmployeeId.getStatusCode(), HttpStatus.OK);
	}

	@Test
	public void deleteEmployeeById_ok_notFound() throws Exception {

		Employee employee = new Employee();

		employee.setId(10);
		employee.setName("Mani Kiran");
		employee.setCity("Hyderabad");
		employee.setPhone(99484822);
		employee.setSal(10000);

		when(employeeService.deleteByEmployeeid(10)).thenReturn(null);

		ResponseEntity<?> deleteByEmployeeId = employeeController.deleteByEmployeeId(10);

		assertEquals(deleteByEmployeeId.getStatusCode(), HttpStatus.EXPECTATION_FAILED);
	}

	@Test
	public void deleteEmployeeById_SqlException_check() throws Exception {

		when(employeeController.deleteByEmployeeId(10)).thenThrow(SQLException.class);

		ResponseEntity<?> deleteByEmployeeId = employeeController.deleteByEmployeeId(10);

		assertEquals(deleteByEmployeeId.getStatusCode(), HttpStatus.CONFLICT);
	}

	@Test
	public void deleteEmployeeById_RecordNotFoundException_check() throws Exception {

		when(employeeController.deleteByEmployeeId(10)).thenThrow(RecordNotFoundException.class);

		ResponseEntity<?> deleteByEmployeeId = employeeController.deleteByEmployeeId(10);

		assertEquals(deleteByEmployeeId.getStatusCode(), HttpStatus.CONFLICT);
	}

	@Test
	public void deleteEmployeeById_Exception_check() throws Exception {

		when(employeeController.deleteByEmployeeId(10)).thenThrow(RuntimeException.class);

		ResponseEntity<?> deleteByEmployeeId = employeeController.deleteByEmployeeId(10);

		assertEquals(deleteByEmployeeId.getStatusCode(), HttpStatus.CONFLICT);
	}

	@Test
	public void updateEmployee_validations() throws Exception {

		Employee employee = null;

		ResponseEntity<?> updateEmployee = employeeController.updateEmployee(null);

		assertEquals(updateEmployee.getStatusCode(), HttpStatus.BAD_REQUEST);
	}

	@Test
	public void updateEmployee_fieldsValidation() throws Exception {

		Employee employee = new Employee();
		employee.setName("");
		employee.setCity("");
		employee.setPhone(null);
		employee.setSal(null);

		ResponseEntity<?> updateEmployee = employeeController.updateEmployee(employee);

		assertEquals(updateEmployee.getStatusCode(), HttpStatus.UNPROCESSABLE_ENTITY);

	}

	@Test
	public void updateEmployee_null_object() throws Exception {

		Employee employee = new Employee();
		employee.setId(10);
		employee.setName("pranay");
		employee.setCity("hyderabad");
		employee.setPhone(9949467);
		employee.setSal(10000);

		when(employeeService.updateEmployee(employee)).thenReturn(null);

		ResponseEntity<?> updateEmployee = employeeController.updateEmployee(employee);

		assertEquals(updateEmployee.getStatusCode(), HttpStatus.EXPECTATION_FAILED);

	}

	@Test
	public void updateEmployee_ok() throws Exception {

		Employee employee = new Employee();
		employee.setId(10);
		employee.setName("pranay");
		employee.setCity("hyderabad");
		employee.setPhone(9949467);
		employee.setSal(10000);

		when(employeeService.updateEmployee(employee)).thenReturn(employee);

		ResponseEntity<?> updateEmployee = employeeController.updateEmployee(employee);

		assertEquals(updateEmployee.getStatusCode(), HttpStatus.OK);

	}

	@Test
	public void updateEmployee_sqlException() throws SQLException, RecordNotFoundException {

		Employee employee = new Employee();
		employee.setName("Mani Kiran");
		employee.setCity("Hyderabad");
		employee.setPhone(9949467);
		employee.setSal(10000);

		when(employeeService.updateEmployee(employee)).thenThrow(SQLException.class);

		ResponseEntity<?> updateEmployee = employeeController.updateEmployee(employee);

		assertEquals(updateEmployee.getStatusCode(), HttpStatus.CONFLICT);

	}

	@Test
	public void updateEmployee_Exception() throws Exception {

		Employee employee = new Employee();

		employee.setName("Mani Kiran");
		employee.setCity("Hyderabad");
		employee.setPhone(9949467);
		employee.setSal(10000);

		when(employeeService.updateEmployee(employee)).thenThrow(new RuntimeException());

		ResponseEntity<?> updateEmployee = employeeController.updateEmployee(employee);

		assertEquals(updateEmployee.getStatusCode(), HttpStatus.CONFLICT);
	}

	@Test
	public void getAllEmployeeInfo_No_search() {

		EmployeeResponseList employeeResponseList = new EmployeeResponseList();

		employeeResponseList.setToatalNoOfElements(3l);
		employeeResponseList.setTotalPages(1);
		java.util.List<Employee> arrayList = new ArrayList<>();
		arrayList.add(new Employee(1, "pranay", 2500, "hyderabad", 78387));
		arrayList.add(new Employee(1, "mani", 3500, "hyderabad", 74387));
		arrayList.add(new Employee(1, "santosh", 2500, "Sydney", 73387));
		employeeResponseList.setList(arrayList);

		when(employeeService.getAllEmployeeDetails(1, 3)).thenReturn(employeeResponseList);

		ResponseEntity<?> allEmployeeInfo = employeeController.getAllEmployeeInfo("null", 1, 3);

		assertEquals(allEmployeeInfo.getStatusCode(), HttpStatus.OK);

	}

	@Test
	public void getAllEmployeeInfo_Search() {

		EmployeeResponseList employeeResponseList = new EmployeeResponseList();

		employeeResponseList.setToatalNoOfElements(1);
		employeeResponseList.setTotalPages(0);
		java.util.List<Employee> arrayList = new ArrayList<>();
		arrayList.add(new Employee(1, "pranay", 2500, "hyderabad", 78387));
		arrayList.add(new Employee(2, "Mani Kiran", 3500, "hyderabad", 74387));
		employeeResponseList.setList(arrayList);

		when(employeeService.getAllEmployeeDetails("pranay", 1, 3)).thenReturn(employeeResponseList);

		ResponseEntity<?> allEmployeeInfo = employeeController.getAllEmployeeInfo("pranay", 1, 3);

		assertEquals(allEmployeeInfo.getStatusCode(), HttpStatus.OK);

	}

	@Test
	public void getAllEmployeeInfo_null_check() {

		ResponseEntity<?> allEmployeeInfo = employeeController.getAllEmployeeInfo("undefined", null, 3);

		assertEquals(allEmployeeInfo.getStatusCode(), HttpStatus.BAD_REQUEST);

	}

	@Test
	public void getAllEmployeeInfo_Exception() {

		when(employeeService.getAllEmployeeDetails("pranay", 1, 3)).thenThrow(RuntimeException.class);

		ResponseEntity<?> allEmployeeInfo = employeeController.getAllEmployeeInfo("pranay", 1, 3);

		assertEquals(allEmployeeInfo.getStatusCode(), HttpStatus.CONFLICT);

	}

	@Test
	public void getAllEmployeesInfoByCity_search() {

		EmployeeResponseList employeeResponseList = new EmployeeResponseList();
		employeeResponseList.setToatalNoOfElements(2l);
		employeeResponseList.setTotalPages(1);
		ArrayList<Employee> list = new ArrayList<Employee>();
		list.add(new Employee(1, "Pranay", 2500, "Hyderabad", 77885599));
		list.add(new Employee(2, "Mani Kiran", 3500, "Hyderbad", 88994455));
		employeeResponseList.setList(list);

		when(employeeService.getAllEmployeeDetailsByCity("Hyderabad", 1, 2)).thenReturn(employeeResponseList);

		ResponseEntity<?> allEmployeeInfoByCity = employeeController.getAllEmployeeInfoByCity("Hyderabad", 1, 2);

		assertEquals(allEmployeeInfoByCity.getStatusCode(), HttpStatus.OK);
	}

	@Test
	public void getAllEmployeesInfoByCity_NoSearch() {

		EmployeeResponseList employeeResponseList = new EmployeeResponseList();
		employeeResponseList.setToatalNoOfElements(2l);
		employeeResponseList.setTotalPages(1);
		ArrayList<Employee> list = new ArrayList<Employee>();
		list.add(new Employee(1, "Pranay", 2500, "Hyderabad", 77885599));
		list.add(new Employee(2, "Mani Kiran", 3500, "Hyderbad", 88994455));
		employeeResponseList.setList(list);

		when(employeeService.getAllEmployeeDetails(1, 2)).thenReturn(employeeResponseList);

		ResponseEntity<?> allEmployeeInfoByCity = employeeController.getAllEmployeeInfoByCity("null", 1, 2);

		assertEquals(allEmployeeInfoByCity.getStatusCode(), HttpStatus.OK);
	}

	@Test
	public void getAllEmployeesInfoByCity_null() {

		ResponseEntity<?> allEmployeeInfoByCity = employeeController.getAllEmployeeInfoByCity("Mani Kiran", null, null);

		assertEquals(allEmployeeInfoByCity.getStatusCode(), HttpStatus.BAD_REQUEST);
	}

	@Test
	public void getAllEmployeesByCity_Exception() {

		when(employeeService.getAllEmployeeDetailsByCity("Hyderabad", 1, 2)).thenThrow(RuntimeException.class);

		ResponseEntity<?> allEmployeeInfoByCity = employeeController.getAllEmployeeInfoByCity("Hyderabad", 1, 2);

		assertEquals(allEmployeeInfoByCity.getStatusCode(), HttpStatus.CONFLICT);
	}

	@Test
	public void getEmployeeDetailsByPhone() throws RecordNotFoundException {

		Employee employee = new Employee();

		employee.setId(10);
		employee.setName("Mani Kiran");
		employee.setCity("Hyderabad");
		employee.setPhone(99484822);
		employee.setSal(10000);

		when(employeeService.findByPhone(99484822)).thenReturn(employee);

		ResponseEntity<?> employeeDetailsByPhone = employeeController.getEmployeeDetailsByPhone(99484822);

		assertEquals(employeeDetailsByPhone.getStatusCode(), HttpStatus.OK);
	}

	@Test

	public void getEmployeeDetailsByPhone_0() throws RecordNotFoundException {

		ResponseEntity<?> employeeDetailsByPhone = employeeController.getEmployeeDetailsByPhone(0);

		assertEquals(employeeDetailsByPhone.getStatusCode(), HttpStatus.BAD_REQUEST);

	}

	@Test
	public void getEmployeeDetailsByPhone_null_check() throws RecordNotFoundException {
		Employee employee = new Employee();

		employee.setId(10);
		employee.setName("Mani Kiran");
		employee.setCity("Hyderabad");
		employee.setPhone(99484822);
		employee.setSal(10000);

		when(employeeService.findByPhone(99484822)).thenReturn(null);

		ResponseEntity<?> employeeDetailsByPhone = employeeController.getEmployeeDetailsByPhone(99484822);
		
		assertEquals(employeeDetailsByPhone.getStatusCode(), HttpStatus.BAD_REQUEST);
	}

	@Test
	public void getEmployeeDetailsByPhone_Exception() throws RecordNotFoundException {

		when(employeeService.findByPhone(99484822)).thenThrow(RuntimeException.class);

		ResponseEntity<?> employeeDetailsByPhone = employeeController.getEmployeeDetailsByPhone(99484822);

		assertEquals(employeeDetailsByPhone.getStatusCode(), HttpStatus.CONFLICT);
	}
	@Test
	public void getAllEmployeesBySalary() {
		
		ResponseEntity<?> allEmployeeInfoBySalary = employeeController.getAllEmployeeInfoBySalary(2500, null, null);
		
		assertEquals(allEmployeeInfoBySalary.getStatusCode(), HttpStatus.BAD_REQUEST);
	}
	@Test
	public void getAllEmployeesBysalary_pageno_pagesize() {
		
		EmployeeResponseList employeeResponseList = new EmployeeResponseList();
		employeeResponseList.setToatalNoOfElements(21);
		employeeResponseList.setTotalPages(2);
		ArrayList<Employee> arrayList = new ArrayList<>();
		arrayList.add(new Employee(1, "Pranay", 25000, "Hyderabad", 88997744));
		arrayList.add(new Employee(2, "Mani Kiran", 25000, "Hyderabad", 99887744));
		employeeResponseList.setList(arrayList);
		when(employeeService.getAllEmployeeDetailsBySalary(null, 0, 1)).thenReturn(employeeResponseList);
		
		ResponseEntity<?> allEmployeeInfoBySalary = employeeController.getAllEmployeeInfoBySalary(0, 0, 1);
		
		assertEquals(allEmployeeInfoBySalary.getStatusCode(), HttpStatus.OK);
	}
	@Test
	public void getAllEmployeesBysalary_pageno_pagesize_search() {
		
		EmployeeResponseList employeeResponseList = new EmployeeResponseList();
		employeeResponseList.setToatalNoOfElements(21);
		employeeResponseList.setTotalPages(2);
		ArrayList<Employee> arrayList = new ArrayList<>();
		arrayList.add(new Employee(1, "Pranay", 25000, "Hyderabad", 88997744));
		arrayList.add(new Employee(2, "Mani Kiran", 25000, "Hyderabad", 99887744));
		employeeResponseList.setList(arrayList);
		when(employeeService.getAllEmployeeDetailsBySalary(null, 0, 1)).thenReturn(employeeResponseList);
		
		ResponseEntity<?> allEmployeeInfoBySalary = employeeController.getAllEmployeeInfoBySalary(25000, 0, 1);
		
		assertEquals(allEmployeeInfoBySalary.getStatusCode(), HttpStatus.OK);
	}
	@Test
	public void getAllEmployeesBysalary_Exception() throws RecordNotFoundException {

		when(employeeService.getAllEmployeeDetailsBySalary(25000, 0, 1)).thenThrow(RuntimeException.class);

 ResponseEntity<?> allEmployeeInfoBySalary = employeeController.getAllEmployeeInfoBySalary(25000, 0, 1);
		assertEquals(allEmployeeInfoBySalary.getStatusCode(), HttpStatus.CONFLICT);
	}
	
}
