package com.evertix.financialwallet.config;

import com.evertix.financialwallet.model.EconomicActivity;
import com.evertix.financialwallet.model.Enterprise;
import com.evertix.financialwallet.model.Role;
import com.evertix.financialwallet.model.enums.ERole;
import com.evertix.financialwallet.repository.EconomicActivityRepository;
import com.evertix.financialwallet.repository.EnterpriseRepository;
import com.evertix.financialwallet.repository.RoleRepository;
import com.evertix.financialwallet.repository.UserRepository;
import com.evertix.financialwallet.security.request.SignUpRequest;
import com.evertix.financialwallet.service.AuthService;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataLoader {
    private final RoleRepository roleRepository;
    private final AuthService authService;
    private final UserRepository userRepository;
    private final EconomicActivityRepository economicActivityRepository;
    private final EnterpriseRepository enterpriseRepository;

    public DataLoader(RoleRepository roleRepository, AuthService authService, UserRepository userRepository,
                      EconomicActivityRepository economicActivityRepository, EnterpriseRepository enterpriseRepository) {
        this.roleRepository = roleRepository;
        this.authService = authService;
        this.userRepository = userRepository;
        this.economicActivityRepository = economicActivityRepository;
        this.enterpriseRepository = enterpriseRepository;
        this.loadData();
    }

    private void loadData() {
        this.addRoles();
        this.addUsers();
        this.addEconomicActivities();
        this.addEnterprises();
    }

    private void addEnterprises() {
        Enterprise firstEnterprise = new Enterprise("145896532", "Coca Cola SAC", "coca.cola@gmail.com", "AV. Arequipa",
                "940178956");
        firstEnterprise.setEconomicActivity(economicActivityRepository.findByName("Bebidas").orElse(null));
        firstEnterprise.setManager(userRepository.findByUsername("MSAlbert").orElse(null));
        enterpriseRepository.save(firstEnterprise);
    }

    private void addEconomicActivities() {
        this.economicActivityRepository.saveAll(Arrays.asList(
                new EconomicActivity("Bebidas"),
                new EconomicActivity("Vehículos & Accesorios"),
                new EconomicActivity("Elaboración de productos Alimenticios"),
                new EconomicActivity("Agricultura, agroindustria y ganadería"),
                new EconomicActivity("Turismo, hoteles, restaurantes y entretenimiento"),
                new EconomicActivity("Servicios de sistemas, equipos de tecnología y comunicaciones")
        ));
    }

    private void addUsers() {
        SignUpRequest firstUser = new SignUpRequest("DHJesús", "password", "dh.jesus@gmail.com", "Jesus",
                "Duran Huancas", "77332215", "995588630");
        this.authService.registerUser(firstUser);

        SignUpRequest secondUser = new SignUpRequest("MSAlbert", "password", "ms.albert@gmail.com", "Albert",
                "Mayta Segovia", "71458215", "995459630");
        this.authService.registerUser(secondUser);
    }

    private void addRoles() {
        this.roleRepository.saveAll(Arrays.asList(
                new Role(ERole.ROLE_ADMIN),
                new Role(ERole.ROLE_USER)
        ));
    }
}
