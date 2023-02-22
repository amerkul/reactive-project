package com.example.project.repository.impl;

import com.example.project.entity.Customer;
import com.example.project.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Repository
@AllArgsConstructor
public class CustomerRepositoryImpl implements CustomerRepository {

    private static final String SELECT_CUSTOMERS = """
            SELECT c.email, u.user_id, u.username, u.password, u.role,
            o.order_id, o.total_cost, o.created,
            b.billing_id, b.first_name, b.last_name,
            o_d.order_details_id, o_d.number, o_d.cost,
            businesses.business_id, businesses.favour, businesses.cost AS businesses_cost,
            p.phone_id, p.number AS phone_number, p.area_code, p.country_code, p.created AS phone_created, p.updated AS phone_updated,
            a.address_id, a.country, a.state, a.state_abbreviation, a.city, a.street, a.zip, a.created AS address_created, a.updated AS address_updated,
            payments.payment_id, payments.amount, payments.created AS payment_created
            FROM customers c
            JOIN users u ON u.user_id = c.user_id
            LEFT JOIN orders o ON o.user_id = c.user_id
            LEFT JOIN billings b ON b.user_id = c.user_id
            LEFT JOIN order_details o_d ON o_d.order_id = o.order_id
            LEFT JOIN businesses ON businesses.business_id = o_d.business_id
            LEFT JOIN phones p ON p.phone_id = b.phone_id
            LEFT JOIN addresses a ON a.address_id = b.address_id
            LEFT JOIN payments ON payments.billing_id = b.billing_id
            """;

    private static final String INSERT_CUSTOMER = """
            INSERT INTO customers(email, user_id) VALUES (:email, :userId)
            """;

    private static final String UPDATE_CUSTOMER = """
            UPDATE customers
            SET email = :email
            WHERE user_id = :userId
            """;


    private final DatabaseClient client;

    @Override
    public Flux<Customer> findAllCustomers() {
        return client.sql(SELECT_CUSTOMERS)
                     .fetch()
                     .all()
                     .bufferUntilChanged(result -> result.get("user_id"))
                     .flatMap(Customer::fromRows);
    }

    @Override
    public Mono<Customer> saveCustomer(long userId, String email) {
        return client.sql(INSERT_CUSTOMER)
                .filter((statement, executeFunction) -> statement.returnGeneratedValues("user_id").execute())
                .bind("email", email)
                .bind("userId", userId)
                .fetch()
                .first()
                .map(row -> new Customer(Long.parseLong(row.get("user_id").toString())));
    }

    @Override
    public Mono<Void> updateCustomer(long userId, String email) {
        return client.sql(UPDATE_CUSTOMER)
                .bind("email", email)
                .bind("userId", userId)
                .fetch()
                .first()
                .then();

    }
}
