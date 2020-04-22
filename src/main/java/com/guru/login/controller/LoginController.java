package com.guru.login.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.validation.Valid;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.guru.login.helper.PageModel;
import com.guru.login.model.Address;
import com.guru.login.model.Lead;
import com.guru.login.model.User;
import com.guru.login.service.LeadService;
import com.guru.login.service.UserService;

@Controller
public class LoginController {

	@Autowired
	private UserService userService;
	@Autowired
	private PageModel pageModel;
	@Autowired
	private LeadService leadService;

	@GetMapping(value = { "/", "/login" })
	public ModelAndView login() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		return modelAndView;
	}

	@GetMapping(value = "/registration")
	public ModelAndView registration() {
		ModelAndView modelAndView = new ModelAndView();
		User user = new User();
		modelAndView.addObject("user", user);
		modelAndView.setViewName("registration");
		return modelAndView;
	}

	@PostMapping(value = "/registration")
	public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		User userExists = userService.findUserByUserName(user.getUserName());
		if (userExists != null) {
			bindingResult.rejectValue("userName", "error.user",
					"There is already a user registered with the user name provided");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("registration");
		} else {
			userService.saveUser(user);
			modelAndView.addObject("successMessage", "User has been registered successfully");
			modelAndView.addObject("user", new User());
			modelAndView.setViewName("registration");

		}
		return modelAndView;
	}

	@PostMapping(value = "/saveLead")
	public ModelAndView createNewLead(@Valid Lead lead, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("lead");
		}
		Address address = lead.getLeadAddress();
		address.setLead(lead);
		leadService.saveLead(lead);
		modelAndView.addObject("successMessage", "Lead has been registered successfully");

		modelAndView.addObject("leadList",
				leadService.findAll(PageRequest.of(pageModel.getPAGE(), pageModel.getSIZE())));
		modelAndView.setViewName("admin/home");

		modelAndView.setViewName("admin/home");

		return modelAndView;
	}

	@GetMapping(value = "/admin/home")
	public ModelAndView home() {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByUserName(auth.getName());
		modelAndView.addObject("userName", "Welcome " + user.getUserName() + "/" + user.getName() + " "
				+ user.getLastName() + " (" + user.getEmail() + ")");
		modelAndView.addObject("adminMessage", "Content Available Only for Users with Admin Role");

		modelAndView.addObject("leadList",
				leadService.findAll(PageRequest.of(pageModel.getPAGE(), pageModel.getSIZE())));
		modelAndView.setViewName("admin/home");

		modelAndView.setViewName("admin/home");
		return modelAndView;
	}

	@GetMapping(value = "/lead")
	public ModelAndView leadPage() {
		ModelAndView modelAndView = new ModelAndView();
		User user = new User();
		modelAndView.addObject("user", user);
		modelAndView.setViewName("lead");
		return modelAndView;
	}

	@GetMapping(value = "/getAllLead")
	public ModelAndView getAlllead() {
		ModelAndView modelAndView = new ModelAndView();
		pageModel.initPageAndSize();
		modelAndView.addObject("leadList",
				leadService.findAll(PageRequest.of(pageModel.getPAGE(), pageModel.getSIZE())));
		modelAndView.setViewName("admin/home");
		return modelAndView;
	}


	@GetMapping(value = "/uploadFile")
	public ModelAndView uploadFile() {
		ModelAndView modelAndView = new ModelAndView();
		User user = new User();
		modelAndView.addObject("user", user);
		modelAndView.setViewName("upload");
		return modelAndView;
	}

	@PostMapping("/import")
	public ModelAndView mapReapExcelDatatoDB(@RequestParam("file") MultipartFile reapExcelDataFile) throws IOException {

		List<Lead> tempStudentList = new ArrayList<Lead>();
		XSSFWorkbook workbook = new XSSFWorkbook(reapExcelDataFile.getInputStream());
		XSSFSheet worksheet = workbook.getSheetAt(0);

		for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {

			Lead lead = new Lead();

			XSSFRow row = worksheet.getRow(i);
			if (!isRowEmpty(row)) {
				try {
					lead.setEventName(row.getCell(0).getStringCellValue());
					lead.setUserName(row.getCell(2).getStringCellValue());
					lead.setCompanyName(row.getCell(3).getStringCellValue());
					lead.setMobile(String.valueOf(row.getCell(4).getNumericCellValue()));
					lead.setMobile1(String.valueOf(row.getCell(5).getNumericCellValue()));
					lead.setMobile2(String.valueOf(row.getCell(6).getNumericCellValue()));
					lead.setEmail1(row.getCell(7).getStringCellValue());
					lead.setEmail2(row.getCell(8).getStringCellValue());
					lead.setEmail3(row.getCell(9).getStringCellValue());
					lead.setCreateDate(new Date());
					lead.setUpdateDate(new Date());

					Address address = new Address();
					address.setAddress1(row.getCell(10).getStringCellValue());
					address.setAddress2(row.getCell(11).getStringCellValue());
					address.setCity(row.getCell(12).getStringCellValue());
					address.setState(row.getCell(13).getStringCellValue());
					address.setCountry(row.getCell(14).getStringCellValue());
					address.setZipcode(String.valueOf(row.getCell(15).getNumericCellValue()));

					lead.setLeadAddress(address);
					address.setLead(lead);

					// leadService.saveLead(lead);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		}

		ModelAndView modelAndView = new ModelAndView();
		List<Lead> leadList = leadService.getAllLead();
		modelAndView.addObject("leadList", leadList);
		modelAndView.setViewName("admin/home");
		return modelAndView;
	}

	private static boolean isRowEmpty(Row row) {
		if (row == null) {
			return true;
		}
		if (row.getLastCellNum() <= 0) {
			return true;
		}
		for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
			Cell cell = row.getCell(c);
			if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK)
				return false;
		}
		return true;
	}

	private static final String FILE_NAME = "/tmp/MyFirstExcel.xlsx";

	public static void main(String[] args) {

		try {

			FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));
			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet datatypeSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = datatypeSheet.iterator();

			while (iterator.hasNext()) {

				Row currentRow = iterator.next();
				Iterator<Cell> cellIterator = currentRow.iterator();

				while (cellIterator.hasNext()) {

					Cell currentCell = cellIterator.next();
					// getCellTypeEnum shown as deprecated for version 3.15
					// getCellTypeEnum ill be renamed to getCellType starting from version 4.0
					if (currentCell.getCellTypeEnum() == CellType.STRING) {
						System.out.print(currentCell.getStringCellValue() + "--");
					} else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
						System.out.print(currentCell.getNumericCellValue() + "--");
					}

				}
				System.out.println();

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
