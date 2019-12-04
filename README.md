# Experimental ISO8583 Payment Application

ISO8583 is an international standard for financial transaction card originated interchange messaging. It is the International Organization for Standardization standard for systems that exchange electronic transactions initiated by cardholders using payment cards.

ISO8583 defines a message format and a communication flow so that different systems can exchange these transaction requests and responses. The vast majority of transactions made when a customer uses a card to make a payment in a store (EFTPOS) use ISO 8583 at some point in the communication chain, as do transactions made at ATMs. In particular, both the MasterCard and Visa networks base their authorization communications on the ISO 8583 standard, as do many other institutions and networks.

Although ISO 8583 defines a common standard, it is not typically used directly by systems or networks. It defines many standard fields (data elements) which remain the same in all systems or networks, and leaves a few additional fields for passing network-specific details. These fields are used by each network to adapt the standard for its own use with custom fields and custom usages.

More: https://en.wikipedia.org/wiki/ISO_8583


To package and deploy to external tomcat server
- checkout the repositior from github
- mvn clean install
- mvn clean dependency:copy-dependencies package
- Open http://localhost:9000/


## Development server
- checkout the repositior from github
- mvn clean install
- mvn spring-boot:run
- Open http://localhost:9000/

The app will automatically reload if you change any of the source files.

## Production server
- checkout the repositior from github
- mvn clean install
- mvn spring-boot:run
- Open http://localhost:9000/

## Prefered Development Envrionment
- Text Editor: VS Code (v1.34 or later)/ IntelliJ IDEA Community 
- VS Code Extensions: Language Support for Java(TM) by Red Hat redhat.java, Spring Boot Extension Pack, Lombok Annotations Support for VS Code



