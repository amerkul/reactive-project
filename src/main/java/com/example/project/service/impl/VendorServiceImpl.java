package com.example.project.service.impl;

import com.example.project.entity.Address;
import com.example.project.entity.Billing;
import com.example.project.entity.Business;
import com.example.project.entity.Phone;
import com.example.project.entity.User;
import com.example.project.entity.Vendor;
import com.example.project.repository.*;
import com.example.project.service.VendorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class VendorServiceImpl implements VendorService {

    private final DefaultVendorRepository vendorRepository;
    private final UserRepository userRepository;
    private final BillingRepository billingRepository;
    private final BusinessRepository businessRepository;
    private final PhoneRepository phoneRepository;
    private final AddressRepository addressRepository;

    @Override
    public Flux<Vendor> retrieveAll() {
        return vendorRepository.findAllVendors();
    }

    @Override
    public Mono<Void> saveVendorWithDetails(Vendor vendor) {
        return userRepository.save(new User(vendor.getRole(), vendor.getUsername(), vendor.getPassword()))
                             .flatMap(savedUser -> vendorRepository.saveVendor(new Vendor(savedUser.getId(), vendor.getAccountNumber(), vendor.getName())))
                             .flatMapMany(savedVendor -> Flux.fromIterable(vendor.getBillings())
                                                             .flatMap(billing -> updateBilling(billing, savedVendor))
                                                             .then(Flux.fromIterable(vendor.getBusinesses())
                                                                       .flatMap(business -> updateBusiness(business, savedVendor))
                                                                       .then()))
                             .then();
    }

    @Override
    public Mono<Void> updateVendor(long id, Vendor newVendor) {
        return userRepository.findById(id)
                             .flatMap(user -> Mono.just(newVendor)
                                                  .map(v -> {
                                                      user.setPassword(v.getPassword());
                                                      user.setRole(v.getRole());
                                                      user.setUsername(v.getUsername());
                                                      return user;
                                                  }))
                             .flatMap(userRepository::save)
                             .flatMapMany(user -> Flux.fromIterable(newVendor.getBillings())
                                                      .flatMap(newBilling -> updateBilling(newBilling, user)))
                             .thenMany(vendorRepository.findById(id)
                                                   .flatMap(vendor -> Mono.just(newVendor)
                                                                          .map(v -> {
                                                                              vendor.setAccountNumber(v.getAccountNumber());
                                                                              vendor.setName(v.getName());
                                                                              return vendor;
                                                                          }))
                                                   .flatMap(vendorRepository::updateVendor)
                                                   .flatMapMany(vendor -> Flux.fromIterable(newVendor.getBusinesses())
                                                                              .flatMap(business -> updateBusiness(business, vendor))))

                             .then();
    }

    private Mono<Billing> updateBilling(Billing billing, User savedVendor) {
        return addressRepository.save(billing.getAddress())
                                .map(savedAddress -> updateBillingAddress(billing, savedAddress))
                                .then(phoneRepository.save(billing.getPhone())
                                                     .map(savedPhone -> updateBillingPhone(billing, savedPhone)))
                                .then(Mono.just(updateBillingUser(billing, savedVendor)))
                                .flatMap(billingRepository::save);
    }

    private Mono<Business> updateBusiness(Business business, Vendor savedVendor) {
        return addressRepository.save(business.getAddress())
                                .map(savedAddress -> updateBusinessAddress(business, savedAddress))
                                .then(phoneRepository.save(business.getPhone())
                                                     .map(savedPhone -> updateBusinessPhone(business, savedPhone)))
                                .then(Mono.just(updateBusinessUser(business, savedVendor)))
                                .flatMap(businessRepository::save);
    }

    private Business updateBusinessUser(Business business, Vendor vendor) {
        business.setUserId(vendor.getId());
        return business;
    }

    private Business updateBusinessAddress(Business business, Address address) {
        business.setAddressId(address.getId());
        return business;
    }

    private Business updateBusinessPhone(Business business, Phone phone) {
        business.setPhoneId(phone.getId());
        return business;
    }

    private Billing updateBillingUser(Billing billing, User vendor) {
        billing.setUserId(vendor.getId());
        return billing;
    }

    private Billing updateBillingAddress(Billing billing, Address address) {
        billing.setAddressId(address.getId());
        return billing;
    }

    private Billing updateBillingPhone(Billing billing, Phone phone) {
        billing.setPhoneId(phone.getId());
        return billing;
    }

}
