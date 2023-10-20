package architecture;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static architecture.NamingConventions.API_PACKAGE;
import static architecture.NamingConventions.BASE_PACKAGE;
import static architecture.NamingConventions.DOMAIN_PACKAGE;
import static architecture.NamingConventions.PERSISTENCE_PACKAGE;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(packages = BASE_PACKAGE, importOptions = ImportOption.DoNotIncludeTests.class)
@SuppressWarnings("unused")
class ArchitectureConventionsTest {
    @ArchTest
    static final ArchRule domainShouldHaveStandardDependencies =
            classes().that().resideInAPackage(DOMAIN_PACKAGE)
                    .should().onlyDependOnClassesThat().resideInAnyPackage(
                            // Common packages
                            "java..",
                            "lombok..",
                            "org.slf4j..",
                            "org.apache.commons..",
                            "org.springframework..",
                            "com.fasterxml.jackson..",
                            DOMAIN_PACKAGE);

    @ArchTest
    static final ArchRule repositoriesShouldNotDependOnServices =
            noClasses()
                    .that()
                    .haveSimpleNameEndingWith("Repository")
                    .should().dependOnClassesThat()
                    .haveSimpleNameEndingWith("Service")
                    .because("Repositories should not depend on services");

    @ArchTest
    static final ArchRule apiShouldNotDependOnPersistence =
            noClasses()
                    .that()
                    .resideInAPackage(API_PACKAGE)
                    .should().dependOnClassesThat()
                    .resideInAnyPackage(PERSISTENCE_PACKAGE)
                    .because("API should not depend on persistence");

    @ArchTest
    static final ArchRule onlyPersistenceShouldAccessSpringDataHibernateAndJpa =
            noClasses()
                    .that()
                    .resideOutsideOfPackage(PERSISTENCE_PACKAGE)
                    .should().dependOnClassesThat()
                    .resideInAnyPackage(
                            "org.hibernate.common..",
                            "org.hibernate.orm..",
                            "org.hibernate.envers..",
                            "org.springframework.orm..",
                            "org.springframework.data..",
                            "jakarta.persistence..")
                    .because("Only persistence should access persistence related third party libraries");
}
