package com.example.project.controller;

import com.example.project.dto.VendorDto;
import com.example.project.entity.Vendor;
import com.example.project.service.VendorService;
import com.example.project.util.ModelMapperUtil;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@RequestMapping("/vendor")
public class VendorController {

    private final ModelMapperUtil mapperUtil;
    private final VendorService vendorService;

    @PostMapping("/create")
    public Mono<Void> create(@RequestBody VendorDto vendorDto) {
        return vendorService.saveVendorWithDetails(mapperUtil.entityBuilder(vendorDto, Vendor.class));
    }

    @PutMapping("/update/{id}")
    public Mono<Void> update(@PathVariable("id") long id, @RequestBody VendorDto vendorDto) {
        return vendorService.updateVendor(id, mapperUtil.entityBuilder(vendorDto, Vendor.class));
    }

}
