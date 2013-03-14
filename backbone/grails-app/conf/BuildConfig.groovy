grails.servlet.version = "2.5"
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.target.level = 1.6
grails.project.source.level = 1.6

grails.plugin.location.'service-security-ui' = "../service-security-ui"

grails.project.dependency.resolution = {
    inherits("global") {
    }
    
    log "error"
    checksums true
    
    def gebVersion = "0.7.2"
	def seleniumVersion = "2.27.0"

    repositories {
        inherits true
        grailsPlugins()
        grailsHome()
        grailsCentral()
        mavenCentral()
    }
    
    dependencies {
        compile 'org.apache.activemq:activemq-core:5.6.0'
		test("org.seleniumhq.selenium:selenium-htmlunit-driver:$seleniumVersion") {
			exclude "xml-apis"
		}
		test "org.seleniumhq.selenium:selenium-chrome-driver:$seleniumVersion"
		test "org.seleniumhq.selenium:selenium-firefox-driver:$seleniumVersion"
		test "org.seleniumhq.selenium:selenium-support:$seleniumVersion"
        test "org.codehaus.geb:geb-spock:$gebVersion"
    }

    plugins {
        runtime ":hibernate:$grailsVersion"
        runtime ":jquery:1.7.1"
        runtime ":resources:1.1.6"
        build ":tomcat:$grailsVersion"
        
        compile ":spring-security-core:1.2.7.3"
        test ":geb:0.6.3"
        test ":spock:0.6";
    }
}
