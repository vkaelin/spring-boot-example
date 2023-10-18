package architecture;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static architecture.NamingConventions.BASE_PACKAGE;
import static architecture.NamingConventions.DOMAIN_PACKAGE;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@AnalyzeClasses(packages = BASE_PACKAGE, importOptions = ImportOption.DoNotIncludeTests.class)
class DomainConventionsTest {
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
}
