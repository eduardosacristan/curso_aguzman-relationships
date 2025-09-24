package com.andres.cursospringboot.jpa.springboot_jpa_relationship;
import com.andres.cursospringboot.jpa.springboot_jpa_relationship.repositories.InvoiceRepository;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import com.andres.cursospringboot.jpa.springboot_jpa_relationship.entities.Address;
import com.andres.cursospringboot.jpa.springboot_jpa_relationship.entities.Client;
import com.andres.cursospringboot.jpa.springboot_jpa_relationship.entities.Invoice;
import com.andres.cursospringboot.jpa.springboot_jpa_relationship.repositories.ClientRepository;

@SpringBootApplication
public class SpringbootJpaRelationshipApplication implements CommandLineRunner{

	@Autowired
    private  InvoiceRepository invoiceRepository;

	@Autowired
	private ClientRepository clientRepository;	

    SpringbootJpaRelationshipApplication(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

	public static void main(String[] args) {
		SpringApplication.run(SpringbootJpaRelationshipApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//manyToOne();
		//manyToOneFindByIdClient();
		oneToMany();
	}

	@Transactional
	public void oneToMany() {
		Client client = new Client("Fran", "Moras");
		Address addess1 = new Address("El vergel", 1234);
		Address addess2 = new Address("Vasco de Gama", 34);

		client.getAdresses().add(addess1);
		client.getAdresses().add(addess2);

		clientRepository.save(client);
		System.out.println(client);
	}

	@Transactional
	public void manyToOne() {
		Client cliente1 = new Client("John", "Doe");
		clientRepository.save(cliente1);

		Invoice invoice = new Invoice("Compras de oficina", 2000L);
		invoice.setClient(cliente1);
		Invoice invoiceDB = invoiceRepository.save(invoice);
		System.out.println(invoiceDB);

	}

	@Transactional
	public void manyToOneFindByIdClient() {
		Optional<Client> optionalClient = clientRepository.findById(1L);
		if(optionalClient.isPresent()) {
			Client client = optionalClient.orElseThrow();
			
			Invoice invoice = new Invoice("Compras de oficina", 2000L);
			invoice.setClient(client);
			Invoice invoiceDB = invoiceRepository.save(invoice);
			System.out.println(invoiceDB);
		}
	}
	
}
