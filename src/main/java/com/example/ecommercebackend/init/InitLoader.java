package com.example.ecommercebackend.init;

import com.example.ecommercebackend.entity.user.City;
import com.example.ecommercebackend.entity.user.District;
import com.example.ecommercebackend.repository.product.shipping.CountryRepository;
import com.example.ecommercebackend.repository.user.CityRepository;
import com.example.ecommercebackend.repository.user.DistrictRepository;
import com.example.ecommercebackend.repository.user.RoleRepository;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;

@Service
public class InitLoader {
    private final DataSource dataSource;
    private final CountryRepository countryRepository;
    private final CityRepository cityRepository;
    private final DistrictRepository districtRepository;
    private final RoleRepository roleRepository;

    public InitLoader(DataSource dataSource, CountryRepository countryRepository, CityRepository cityRepository, DistrictRepository districtRepository, RoleRepository roleRepository) {
        this.dataSource = dataSource;
        this.countryRepository = countryRepository;
        this.cityRepository = cityRepository;
        this.districtRepository = districtRepository;
        this.roleRepository = roleRepository;
    }

    public void loadInitialCountrySql() throws Exception {
        if (countryRepository.count() == 0) {
            runSqlScript("sql/init/countries.sql");
            runSqlScript("/sql/init/extension.sql");
            runSqlScript("/sql/init/index.sql");
        }

        if (cityRepository.count() == 0) {
            runSqlScript("sql/init/city.sql");
        }

        if (districtRepository.count() == 0) {
            runSqlScript("sql/init/district.sql");
        }

        if (roleRepository.count() == 0) {
            runSqlScript("sql/init/roles.sql");
        }

    }

    private void runSqlScript(String path) throws Exception {
        Resource resource = new ClassPathResource(path);
        try (Connection conn = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(conn, resource);
        }
    }
}
