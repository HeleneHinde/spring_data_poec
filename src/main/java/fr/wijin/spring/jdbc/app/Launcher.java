package fr.wijin.spring.jdbc.app;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import fr.wijin.spring.jdbc.config.AppConfig;
import fr.wijin.spring.jdbc.model.Customer;
import fr.wijin.spring.jdbc.service.CustomerService;

public class Launcher {

	public static void main(String[] args) {
		ApplicationContext context = 
					new AnnotationConfigApplicationContext(
							AppConfig.class);
		CustomerService service = context.getBean(CustomerService.class);
		// System.out.println(service);
		
/* 		DataSource ds = context.getBean(DataSource.class);
		try (
			var cnx = ds.getConnection();
			var pst = cnx.prepareStatement("""
					select * 
					from CUSTOMERS
					""");
			var rs = pst.executeQuery();
		) {
			while (rs.next()) {
				Customer customer = new Customer();
				customer.setFirstname(rs.getString("FIRSTNAME"));
				customer.setLastname(rs.getString("LASTNAME"));
				customer.setId(rs.getInt("ID"));
				customer.setActive(rs.getBoolean("ACTIVE"));
				customer.setMail(rs.getString("MAIL"));
				customer.setNotes(rs.getString("NOTES"));
				customer.setCompany(rs.getString("COMPANY"));
				customer.setPhone(rs.getString("PHONE"));
				System.out.println(customer);
			}
			System.out.println("Nombre de colonnes : " 
					+ rs.getMetaData().getColumnCount());
		} catch (SQLException e) {
			e.printStackTrace();
		} */

		Customer customer = new Customer();
		customer.setFirstname("Jane");
		customer.setLastname("Dark");
		customer.setMail("jane.dark@example.com");
		customer.setPhone("1234567890");
		customer.setCompany("Example Corp");
		customer.setActive(true);
		customer.setNotes("No notes");
		System.out.println(service.insertSimpleCustomer(customer));
		System.out.println(service.getCustomerCount());
		System.out.println(service.getAllCustomers());

	}
}
