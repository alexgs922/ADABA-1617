
package services;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.CustomerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Customer;

@Service
@Transactional
public class CustomerService {

	// ---------- Repositories----------------------

	@Autowired
	private CustomerRepository	customerRepository;

	// Supporting services ------------------------------------------

	@Autowired
	private Md5PasswordEncoder	encoder;

	@Autowired
	private Validator			validator;


	// Simple CRUD methods ----------------------------------------------------

	public Customer create() {
		Customer result;

		result = new Customer();

		return result;
	}

	public Customer reconstruct(final RegistrationForm customerForm) {

		Customer result;
		UserAccount userAccount;
		Authority authority;
		Collection<Authority> authorities;
		String pwdHash;

		result = this.create();
		authorities = new HashSet<Authority>();
		userAccount = new UserAccount();

		/* NOTA */
		result.setName(customerForm.getName());
		result.setSurname(customerForm.getSurname());
		/* Hay que juntar el nombre y los apellidos para que sea fullName, lo dejo asi porque en los formularios suele ser por separado. */
		result.setPhone(customerForm.getPhone());
		result.setEmail(customerForm.getEmail());

		authority = new Authority();
		authority.setAuthority(Authority.CUSTOMER);
		authorities.add(authority);

		pwdHash = this.encoder.encodePassword(customerForm.getPassword(), null);

		userAccount.setAuthorities(authorities);
		userAccount.setPassword(pwdHash);
		userAccount.setUsername(customerForm.getUsername());
		result.setUserAccount(userAccount);

		return result;

	}

	public Customer reconstruct(final Customer customer, final BindingResult binding) {
		Customer result;

		if (customer.getId() == 0)
			result = customer;
		else {
			result = this.customerRepository.findOne(customer.getId());
			result = new Customer();
			result.setFullName(customer.getFullName());
			result.setEmail(customer.getEmail());
			result.setPhone(customer.getPhone());

			this.validator.validate(result, binding);
		}
		return result;

	}

	public Collection<Customer> findAll() {
		Collection<Customer> res;
		res = this.customerRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Customer findOne(final int customerId) {
		Customer res;

		res = this.customerRepository.findOne(customerId);
		Assert.notNull(res);

		return res;
	}

	public Customer save(final Customer customer) {
		Assert.notNull(customer);

		return this.customerRepository.save(customer);
	}

	public void delete(final Customer customer) {
		Assert.notNull(customer);
		Assert.isTrue(customer.getId() != 0);

		this.customerRepository.delete(customer);

	}

	// Other business methods -----------------------------

	public Customer findByPrincipal() {
		Customer res;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		res = this.findByUserAccount(userAccount);

		return res;
	}

	public Customer findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);

		Customer res;

		res = this.customerRepository.findByUserAccountId(userAccount.getId());

		return res;
	}

}
