package com.example.demo.Service;

import java.awt.print.Pageable;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.demo.Dao.EmployeeDao;
import com.example.demo.Exception.RecordNotFoundException;
import com.example.demo.Model.Employee;
import com.example.demo.Response.EmployeeResponseList;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeDao employeeDao;

	public Employee addEmployee(Employee employee) throws SQLException {

		Employee save = employeeDao.save(employee);
		return save;

	}

	public Employee updateEmployee(Employee employee) throws  SQLException, RecordNotFoundException {

		Optional<Employee> optional = employeeDao.findById(employee.getId());
		if (optional.isPresent()) {
			Employee save = employeeDao.save(employee);
			return save;
		}
		else {
			throw new RecordNotFoundException("No Employee data Present with this employeeid : " + employee.getId());
		}
		

	}

	public Employee getByEmployeeId(Integer employeeid) throws RecordNotFoundException, SQLException {

		Optional<Employee> optional = employeeDao.findById(employeeid);
		if (optional.isPresent()) {
			return optional.get();
		} else {
			throw new RecordNotFoundException("No Employee data Present with this employeeid : " + employeeid);
		}
	}

	public Employee deleteByEmployeeid(Integer employeeid) throws RecordNotFoundException, SQLException {

		Optional<Employee> optional = employeeDao.findById(employeeid);

		if (optional.isPresent()) {

			return optional.get();

		} else {

			throw new RecordNotFoundException(
					"No Employee data Present with this employeeid : " + employeeid + " to Delete");
		}
	}

	public EmployeeResponseList getAllEmployeeData(Integer pageno, Integer pagesize) throws SQLException {

		PageRequest pageRequest = PageRequest.of(pageno, pagesize);
		Page<Employee> page = employeeDao.findAll(pageRequest);
		long totalElements = page.getTotalElements();
		int totalPages = page.getTotalPages();
		List<Employee> list = page.getContent();

		EmployeeResponseList responseList = new EmployeeResponseList();
		responseList.setToatalNoOfElements(totalElements);
		responseList.setTotalPages(totalPages);
		responseList.setList(list);
		return responseList;
	}

	public EmployeeResponseList getAllEmployeeDetails(String search, int pageNo, int sizePerPage) {

		org.springframework.data.domain.Pageable paging = PageRequest.of(pageNo, sizePerPage);

		Page<Employee> page = employeeDao.findByNameContaining(search, paging);

		int totalPages = page.getTotalPages();
		System.out.println(totalPages);
		long totalElements = page.getTotalElements();
		System.out.println(totalElements);
		List<Employee> list = page.getContent();

		EmployeeResponseList response = new EmployeeResponseList();
		response.setToatalNoOfElements(totalElements);
		response.setTotalPages(totalPages);
		response.setList(list);
		return response;

	}

	public EmployeeResponseList getAllEmployeeDetails(int pageNo, int sizePerPage) {
		org.springframework.data.domain.Pageable paging = PageRequest.of(pageNo, sizePerPage);

		Page<Employee> page = employeeDao.findAll(paging);

		List<Employee> list = page.getContent();

		int totalPages = page.getTotalPages();

		long totalElements = page.getTotalElements();

		EmployeeResponseList response = new EmployeeResponseList();
		response.setToatalNoOfElements(totalElements);
		response.setTotalPages(totalPages);
		response.setList(list);
		return response;
	}

	public EmployeeResponseList getAllEmployeeDetailsBySalary(Integer search, int pageNo, int sizePerPage) {

		org.springframework.data.domain.Pageable paging = PageRequest.of(pageNo, sizePerPage);

		Page<Employee> page = employeeDao.findBySal(search, paging);

		int totalPages = page.getTotalPages();
		System.out.println(totalPages);
		long totalElements = page.getTotalElements();
		System.out.println(totalElements);
		List<Employee> list = page.getContent();

		EmployeeResponseList response = new EmployeeResponseList();
		response.setToatalNoOfElements(totalElements);
		response.setTotalPages(totalPages);
		response.setList(list);
		return response;

	}

	public EmployeeResponseList getAllEmployeeDetailsByCity(String search, int pageNo, int sizePerPage) {

		org.springframework.data.domain.Pageable paging = PageRequest.of(pageNo, sizePerPage);

		Page<Employee> page = employeeDao.findByCityContaining(search, paging);

		int totalPages = page.getTotalPages();
		System.out.println(totalPages);
		long totalElements = page.getTotalElements();
		System.out.println(totalElements);
		List<Employee> list = page.getContent();

		EmployeeResponseList response = new EmployeeResponseList();
		response.setToatalNoOfElements(totalElements);
		response.setTotalPages(totalPages);
		response.setList(list);
		return response;

	}

	public Employee findByPhone(int phone) throws RecordNotFoundException {
		Optional<Employee> employeeByPhone = employeeDao.findByPhone(phone);
		if (employeeByPhone.isPresent()) {
			return employeeByPhone.get();
		} else {
			throw new RecordNotFoundException(
					"No Employee data Present with this PhoneNumber : " + phone );
		}

	}

}
