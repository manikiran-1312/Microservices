package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.example.demo.Dao.EmployeeDao;
import com.example.demo.Exception.RecordNotFoundException;
import com.example.demo.Model.Employee;
import com.example.demo.Response.EmployeeResponseList;
import com.example.demo.Service.EmployeeService;

public class EmployeeServiceTests {

	@InjectMocks
	private EmployeeService employeeService;

	@Mock
	private EmployeeDao employeeDao;

	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void createEmployee() throws Exception {
		Employee employee = new Employee();
		employee.setName("pranay");
		employee.setCity("hyderabad");
		employee.setPhone(9949467);
		employee.setSal(10000);

		when(employeeDao.save(employee)).thenReturn(employee);

		Employee addEmployee = employeeService.addEmployee(employee);

		assertEquals(addEmployee, employee);
	}

	@Test
	public void getEmployee() throws SQLException, RecordNotFoundException {

		Employee employee = new Employee();
		employee.setId(10);
		employee.setName("pranay");
		employee.setCity("hyderabad");
		employee.setPhone(9949467);
		employee.setSal(10000);

		when(employeeDao.findById(10)).thenReturn(Optional.ofNullable(employee));

		Employee byEmployeeId = employeeService.getByEmployeeId(10);

		assertEquals(byEmployeeId, employee);

	}

	@Test
	public void getEmployee_RecordNotFoundException_Check() throws SQLException, RecordNotFoundException {

		when(employeeDao.findById(10)).thenReturn(Optional.ofNullable(null));

		assertThrows(RecordNotFoundException.class, () -> employeeService.getByEmployeeId(10));

	}

	@Test
	public void updateEmployee() throws SQLException, RecordNotFoundException {

	

		Employee employee1 = new Employee();
		employee1.setId(10);
		employee1.setName("Pranay");
		employee1.setCity("hyderabad");
		employee1.setPhone(9949467);
		employee1.setSal(10000);

		Optional<Employee> of = Optional.ofNullable(employee1);

		when(employeeDao.findById(10)).thenReturn(of);

		when(employeeDao.save(employee1)).thenReturn(employee1);

		Employee updateEmployee = employeeService.updateEmployee(employee1);

		assertEquals(updateEmployee, employee1);

	}

	@Test
	public void updateEmployee_NoRecordFoundException() throws SQLException, RecordNotFoundException {

		Employee employee1 = new Employee();
		employee1.setId(10);
		employee1.setName("naveen");
		employee1.setCity("hyderabad");
		employee1.setPhone(9949467);
		employee1.setSal(10000);

		when(employeeDao.findById(10)).thenReturn(Optional.ofNullable(null));

		assertThrows(RecordNotFoundException.class, () -> employeeService.updateEmployee(employee1));

	}

	@Test
	public void deleteEmployee() throws SQLException, RecordNotFoundException {

		Employee employee1 = new Employee();
		employee1.setId(10);
		employee1.setName("naveen");
		employee1.setCity("hyderabad");
		employee1.setPhone(9949467);
		employee1.setSal(10000);

		when(employeeDao.findById(10)).thenReturn(Optional.ofNullable(employee1));

		Employee deleteByEmployeeid = employeeService.deleteByEmployeeid(10);

		assertEquals(deleteByEmployeeid, employee1);

	}

	@Test
	public void deleteEmployee_null() throws SQLException, RecordNotFoundException {

		when(employeeDao.findById(10)).thenReturn(Optional.ofNullable(null));

		assertThrows(RecordNotFoundException.class, () -> employeeService.deleteByEmployeeid(10));

	}

	@Test
	public void getAllEmployeeDetails_PageNumber_PageSize() {

		EmployeeResponseList employeeResponseList = new EmployeeResponseList();

		employeeResponseList.setToatalNoOfElements(2l);
		employeeResponseList.setTotalPages(1);
		List<Employee> arrayList = new ArrayList<>();
		arrayList.add(new Employee(1, "pranay", 2500, "hyderabad", 78387));
		arrayList.add(new Employee(2, "Mani Kiran", 3500, "hyderabad", 74387));
		employeeResponseList.setList(arrayList);

		org.springframework.data.domain.Pageable paging = PageRequest.of(1, 3);

		Page<Employee> page = new PageImpl<>(arrayList);

		when(employeeDao.findAll(paging)).thenReturn(page);

		EmployeeResponseList allEmployeeDetails = employeeService.getAllEmployeeDetails(1, 3);

		assertEquals(allEmployeeDetails.getList(), employeeResponseList.getList());

	}

	@Test
	public void getAllEmployeeDetails_PageNumber_PageSize_search() {

		EmployeeResponseList employeeResponseList = new EmployeeResponseList();

		employeeResponseList.setToatalNoOfElements(2l);
		employeeResponseList.setTotalPages(1);
		List<Employee> arrayList = new ArrayList<>();
		arrayList.add(new Employee(1, "pranay", 2500, "hyderabad", 78387));
		arrayList.add(new Employee(2, "Mani Kiran", 3500, "hyderabad", 74387));
		employeeResponseList.setList(arrayList);

		org.springframework.data.domain.Pageable paging = PageRequest.of(1, 3);

		Page<Employee> page = new PageImpl<>(arrayList);

		when(employeeDao.findByNameContaining("pranay", paging)).thenReturn(page);

		EmployeeResponseList allEmployeeDetails = employeeService.getAllEmployeeDetails("pranay", 1, 3);

		assertEquals(allEmployeeDetails.getList(), employeeResponseList.getList());

	}

	@Test
	public void getAllEmployeesData_pageNo_pageSize() throws SQLException {

		EmployeeResponseList employeeResponseList = new EmployeeResponseList();
		employeeResponseList.setToatalNoOfElements(2l);
		employeeResponseList.setTotalPages(1);

		ArrayList<Employee> list = new ArrayList<>();
		list.add(new Employee(1, "Pranay", 25000, "Hyderabad", 88994455));
		list.add(new Employee(2, "Mani Kiran", 35000, "Hyderabad", 99887744));
		employeeResponseList.setList(list);

		org.springframework.data.domain.Pageable of = PageRequest.of(1, 3);

		Page<Employee> pageImpl = new PageImpl<>(list);

		when(employeeDao.findAll(of)).thenReturn(pageImpl);

		EmployeeResponseList allEmployeeData = employeeService.getAllEmployeeData(1, 3);

		assertEquals(allEmployeeData.getList(), employeeResponseList.getList());
	}

	@Test
	public void findByPhone() throws RecordNotFoundException {

		Employee employee1 = new Employee();
		employee1.setId(10);
		employee1.setName("naveen");
		employee1.setCity("hyderabad");
		employee1.setPhone(9949467);
		employee1.setSal(10000);

		when(employeeDao.findByPhone(9949467)).thenReturn(Optional.ofNullable(employee1));

		Employee findByPhone = employeeService.findByPhone(9949467);

		assertEquals(findByPhone, employee1);

	}

	@Test
	public void findByPhone_null() throws RecordNotFoundException, SQLException {

		when(employeeDao.findByPhone(99888)).thenReturn(Optional.ofNullable(null));

		assertThrows(RecordNotFoundException.class, () -> employeeService.findByPhone(99888));

	}

	

	@Test
	public void getAllEmployeesDetailsBySalary() {

		EmployeeResponseList employeeResponseList = new EmployeeResponseList();
		employeeResponseList.setToatalNoOfElements(2l);
		employeeResponseList.setTotalPages(2);

		ArrayList<Employee> list = new ArrayList<>();
		list.add(new Employee(1, "Pranay", 25000, "Hyderabad", 88997744));
		list.add(new Employee(2, "Mani Kiran", 35000, "Hyderabad", 99887744));

		employeeResponseList.setList(list);

		Pageable paging = PageRequest.of(0, 1);

		PageImpl<Employee> page = new PageImpl<>(list);

		when(employeeDao.findBySal(25000, paging)).thenReturn(page);

		EmployeeResponseList allEmployeeDetailsBySalary = employeeService.getAllEmployeeDetailsBySalary(25000, 0, 1);

		assertEquals(allEmployeeDetailsBySalary.getList(), employeeResponseList.getList());

	}

	@Test
	public void getEmployeeDetailsByCityName() {

		EmployeeResponseList employeeResponseList = new EmployeeResponseList();
		employeeResponseList.setToatalNoOfElements(2l);
		employeeResponseList.setTotalPages(1);

		ArrayList<Employee> list = new ArrayList<>();

		list.add(new Employee(1, "Pranay", 25000, "Hyderabad", 88997744));
		list.add(new Employee(2, "Mani Kiran", 35000, "Hyderabad", 99887744));

		employeeResponseList.setList(list);

		Pageable paging = PageRequest.of(0, 1);

		PageImpl<Employee> page = new PageImpl<>(list);

		when(employeeDao.findByCityContaining("Hyderabad", paging)).thenReturn(page);

		EmployeeResponseList allEmployeeDetailsByCity = employeeService.getAllEmployeeDetailsByCity("Hyderabad", 0, 1);

		assertEquals(allEmployeeDetailsByCity.getList(), employeeResponseList.getList());
	}

}