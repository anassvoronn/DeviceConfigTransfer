package org.nastya;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.nastya.entity.Host;
import org.nastya.entity.Interface;
import org.nastya.entity.MyService;
import org.nastya.entity.Policy;
import org.nastya.repository.HostsRepository;
import org.nastya.repository.InterfacesRepository;
import org.nastya.repository.PoliciesRepository;
import org.nastya.repository.ServicesRepository;
import org.nastya.service.ImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
        properties = {
                "spring.jpa.show-sql=false",
                "logging.level.org.hibernate.SQL=OFF",
                "logging.level.org.hibernate.orm.jdbc.bind=OFF"
        }
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
@ActiveProfiles("test")
class ImportServiceTest {

    @Autowired
    private ImportService importService;

    @Autowired
    private HostsRepository hostsRepository;

    @Autowired
    private PoliciesRepository policiesRepository;

    @Autowired
    private ServicesRepository servicesRepository;

    @Autowired
    private InterfacesRepository interfacesRepository;

    @Container
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:16");

    @DynamicPropertySource
    static void configure(DynamicPropertyRegistry registry) {
        postgres.start();
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");

        registry.add("spring.jpa.show-sql", () -> "false");
        registry.add("logging.level.org.hibernate.SQL", () -> "OFF");
        registry.add("logging.level.org.hibernate.orm.jdbc.bind", () -> "OFF");
    }

    @Test
    public void shouldImportJsonTables() throws URISyntaxException {
        Path jsonFile = Paths.get(
                Objects.requireNonNull(
                        getClass().getResource(
                                "/output/device_config_test.json"
                        )
                ).toURI()
        );

        importService.importJson(jsonFile.toString());

        List<Host> hosts = hostsRepository.findAll();

        assertThat(hosts)
                .isNotEmpty()
                .hasSize(10);

        Host host = hosts.stream()
                .filter(item -> item.getName().equals("IP_10.188.51.74"))
                .findFirst()
                .orElseThrow();

        assertThat(host.getId()).isNotNull();
        assertThat(host.getName()).isNotBlank();
        assertThat(host.getIps()).isNotEmpty();
        assertThat(host.getAdditionalProperties()).isNotNull();
        assertThat(host.getType()).isNull();

        Host hostGroup = hosts.stream()
                .filter(item -> item.getName().equals("FW-CR30152"))
                .findFirst()
                .orElseThrow();

        assertThat(hostGroup.getId()).isNotNull();
        assertThat(hostGroup.getName()).isNotBlank();
        assertThat(hostGroup.getMembers()).isNotEmpty();

        List<Policy> policies = policiesRepository.findAll();

        assertThat(policies)
                .isNotEmpty()
                .hasSize(10);

        UUID policyId = UUID.fromString("8D92511C-AB0D-4232-8602-69A17BD3DC56");

        Policy policy = policies.stream()
                .filter(item -> policyId.equals(item.getRuleId()))
                .findFirst()
                .orElseThrow();

        assertThat(policy.getRuleId()).isNotNull();
        assertThat(policy.getRuleName()).isEmpty();
        assertThat(policy.getAction()).isNotNull();
        assertThat(policy.getSrc()).isNotEmpty();
        assertThat(policy.getDst()).isNotEmpty();
        assertThat(policy.getService()).isNotEmpty();
        assertThat(policy.getTarget()).isNotEmpty();
        assertThat(policy.getAdditionalProperties()).isNotNull();

        UUID uninstalledPolicyId = UUID.fromString("8C6E3087-7BAB-48CE-A150-DA5369FFB889");

        Policy uninstalledPolicy = policies.stream()
                .filter(item ->  uninstalledPolicyId.equals(item.getRuleId()))
                .findFirst()
                .orElseThrow();

        assertThat(uninstalledPolicy.getRuleId()).isNotNull();
        assertThat(uninstalledPolicy.getRuleName()).isNull();
        assertThat(uninstalledPolicy.getEnable()).isNotBlank();

        List<MyService> services = servicesRepository.findAll();

        assertThat(services)
                .isNotEmpty()
                .hasSize(10);

        MyService service = services.stream()
                .filter(item -> item.getName().equals("UDP_19223"))
                .findFirst()
                .orElseThrow();

        assertThat(service.getId()).isNotNull();
        assertThat(service.getName()).isNotBlank();
        assertThat(service.getType()).isNull();
        assertThat(service.getServiceDefinitions()).isNotEmpty();
        assertThat(service.getAdditionalProperty()).isNotNull();

        MyService serviceGroup = services.stream()
                .filter(item -> item.getName().equals("FW-CR27869SRV"))
                .findFirst()
                .orElseThrow();

        assertThat(serviceGroup.getId()).isNotNull();
        assertThat(serviceGroup.getName()).isNotBlank();
        assertThat(serviceGroup.getMembers()).isNotEmpty();

        List<Interface> interfaces = interfacesRepository.findAll();

        assertThat(interfaces)
                .isNotEmpty()
                .hasSize(5);

        Interface iface = interfaces.stream()
                .filter(item -> item.getName().equals("eth3-03.904"))
                .findFirst()
                .orElseThrow();

        assertThat(iface.getId()).isNotNull();
        assertThat(iface.getName()).isNotBlank();
        assertThat(iface.getMode()).isNotEmpty();
        assertThat(iface.getNetmask()).isNotEmpty();
        assertThat(iface.getHwdevice()).isNotEmpty();
    }
}