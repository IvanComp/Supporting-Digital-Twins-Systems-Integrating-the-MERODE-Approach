# Supporting Digital Twins Systems Integrating the MERODE Approach

This is a repository for the 3rd International Workshop on Model-Driven Engineering of Digital Twins

# Table of contents
<!--ts-->
   * [The Approach](#The-Approach)
   * [The Generic Domain Model](#The-Generic-Domain-Model)
   * [The Web Application](#The-Web-Application)
   * [The Java Prototype](#The-Java-Prototype)
<!--te-->

# The Approach

MERODE is a model-driven engineering approach that -besides a focus on model quality- includes an incremental development strategy that ensures a well-structured and scalable system that represent an effective base for capturing domain-specific knowledge and establishing effective information systems. This approach relies on the concept of Model-Driven Engineering (MDE) placing a strong focus on ensuring the quality of the data produced and managed by a standard model mapped from the Semantic Sensor Network (SSN) and SOSA Ontology.

<p align="center">
<img src="img/approach.pdf" width="300px" height="150px"/>
</p>


# The Generic Domain Model 

<p align="center">
<img src="img/approach.pdf" width="300px" height="150px"/>
</p>


# The Web Application

The RESTful web application consists of a MAVEN project, offering a range of API services for each business event. The Domain Layer defines several business object types (EDG), each of which has a corresponding set of business event types specified in the Service Layer (OET). These business event types, which encompass actions such as creation, modification, termination, and more, can be performed through API services.

# The Java Prototype

The Java prototype encompasses a straightforward Graphical User Interface (GUI) displaying a list and details of the objects defined in the domain model. It also offers buttons for creating, modifying, and terminating object instances.
