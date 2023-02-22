package com.example.project.repository.impl;

import com.example.project.entity.Vendor;
import com.example.project.repository.VendorRepository;
import lombok.AllArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@AllArgsConstructor
public class VendorRepositoryImpl implements VendorRepository {

    private static final String SELECT_VENDORS = """
            SELECT v.account_number, v.name,
            u.user_id, u.username, u.password, u.role,
            b.billing_id, b.first_name, b.last_name,
            businesses.business_id, businesses.favour, businesses.cost AS businesses_cost,
            p.phone_id, p.number AS phone_number, p.area_code, p.country_code, p.created AS phone_created, p.updated AS phone_updated,
            a.address_id, a.country, a.state, a.state_abbreviation, a.city, a.street, a.zip, a.created AS address_created, a.updated AS address_updated,
            p_business.phone_id AS phone_business_phone_id, p_business.number AS phone_business_number, p_business.area_code AS phone_business_area_code, 
            p_business.country_code AS phone_business_country_code, p_business.created AS phone_business_created,
            p_business.updated AS phone_business_updated,
            a_business.address_id  AS address_business_address_id, a_business.country AS address_business_country, 
            a_business.state AS address_business_state, a_business.state_abbreviation AS address_business_state_abbreviation, 
            a_business.city AS address_business_city, a_business.street AS address_business_street, 
            a_business.zip AS address_business_zip, a_business.created AS address_business_created, 
            a_business.updated AS address_business_updated,
            FROM vendors v
            JOIN users u ON u.user_id = v.user_id
            LEFT JOIN billings b ON b.user_id = v.user_id
            LEFT JOIN businesses ON businesses.user_id = v.user_id
            LEFT JOIN phones p ON p.phone_id = b.phone_id
            LEFT JOIN addresses a ON a.address_id = b.address_id
            LEFT JOIN phones p_business ON p_business.phone_id = businesses.phone_id
            LEFT JOIN addresses a_business ON a_business.address_id = businesses.address_id
            """;

    private static final String INSERT_VENDOR = """
            INSERT INTO vendors(account_number, name, user_id)
            VALUES (:accountNumber, :name, :userId)
            """;

    private static final String UPDATE_VENDOR = """
            UPDATE vendors
            SET account_number = :accountNumber, name = :name
            WHERE user_id = :userId
            """;

    private final DatabaseClient client;

    @Override
    public Mono<Vendor> saveVendor(Vendor vendor) {
        return client.sql(INSERT_VENDOR)
                     .filter((statement, executeFunction) -> statement.returnGeneratedValues("user_id")
                                                                      .execute())
                     .bind("accountNumber", vendor.getAccountNumber())
                     .bind("name", vendor.getName())
                     .bind("userId", vendor.getId())
                     .fetch()
                     .first()
                     .map(row -> new Vendor(Long.parseLong(row.get("user_id")
                                                              .toString())));
    }

    @Override
    public Mono<Vendor> updateVendor(Vendor newVendor) {
        return client.sql(UPDATE_VENDOR)
                     .bind("accountNumber", newVendor.getAccountNumber())
                     .bind("name", newVendor.getName())
                     .bind("userId", newVendor.getId())
                     .fetch()
                     .first()
                     .then(Mono.just(newVendor));
    }

    @Override
    public Flux<Vendor> findAllVendors() {
        return client.sql(SELECT_VENDORS)
                     .fetch()
                     .all()
                     .bufferUntilChanged(result -> result.get("user_id"))
                     .flatMap(Vendor::fromRows);
    }

}
