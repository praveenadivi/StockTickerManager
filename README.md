# StockTickerManager
##What was developed
1. Built an application which can do the following
	1. inserts a Trade into the H2 database
	2. inserts the trades in a file into the H2 database. 
	   It checks for duplicates at the file level by using a MD5SUM on the data in the file so that a file 
	   with the same contents cannot be uploaded again. 
	   This is one of many duplicate checks we can do to reduce accidental uploads. 
	   See "Initial Plan for more details".
	3. find trades based on a ticker symbol.
	4. retrieves all trades.
	5. a lite weight API we can fire from applications like Check-Nanny, etc, to verify if the API is up or not.
2. Rationale in building these APIs
	1. address basic requirements.
	2. demonstrate the ability to plan and deliver.
	3. demonstrate TDD using Junit, Hamcrest, Mockito, API testing. 
	4. demonstrate API documentation using Swagger. 
	5. demonstrate the rationale in using H2 over Mongo. The data is tabular and has a fixed length and therefore better
	suited for relational data.
##How can it be improved further
1. Implement, test and document more CRUD operations
2. Gather more information on the user and his/her needs. The data and the domain and file handling in the question show 
   this better suited to be B2B API rather than a B2C API.
3. Implement better security like usage of signature, salt, OAUTH2/ BASIC/ Spnego /SiteMinder, HTTPS in the APIs
4. Design the application to be more scalable using load balancers, horizontal scaling and benchmarking after 
   load testing
5. By making sure we have a web server like apache where we are running security checks etc.   
##Initial Plan
1. download a project from SpringInitializer
	1. it should have Spring boot
	
	2. it should have Junit and mockito
	
	3. should use an embedded DB like Derby - the data is stock data optimal for long term storage and frequently prone to be warehoused. 
	Hence a relational DB instead of NoSQL. Also the structure is not epheremal and in a normal environment we will need to have replication. 
	Also, this data does not have long Strings and hence it may not be optimal for 	NOSQL
	
	4. should use Spring data or JPA
	
	5. It will have Services with @Transactional from Spring
	6. It will have a Post method to add the data in a batch format - should use Spring batch or a batch function from Spring data for insertion. 
	The file should be sent via HTTP via a multipart request.
	7. It will have a get API for retrieving the records
	8. It will have another Post API for adding the record  
2. I will plan to enhance this design after the excercise by taking care of the following. I am omitting the following for now.
	1. no HTTPS on the APIs - should normally be handled at the proxy level (Apache)
	
	2. no special Signature calculations are being added. The usecase asks for accomodating multiple users although this kind of use case with bulk updates is best suited to
	another system calling this API in that case I would normally whitelist the IP address and add a signature calculation based on 
		1. time
		2. userId/ SystemId/ ProId
		3. Salt
		4. shared Key
	I would take the above information and verify if the application is authorized and if the request is in a specified time 
	window to be processed (I may not want to process hour old requests).

	3. I am not doing anything preemptive like keeping track of how many requests were made by a user 
	and if the MD5Sum of the file & file name provided was already provided in the recent past. I would normally do this in this kind of 
	an application with bulk uploads and potential for duplicates.
	
	4. I would normally deploy an application like this on a cluster of servers that have 8 GB RAM. These will be supported by a load balancer & proxy that will take care of 
	distributing the requests in a round robin fashion and have a security monitoring software on the proxy that will take care of filtering some potentially harmful requests
	like a apache commons issue like tomcat crashing because the delimiter in the multipart request is bigger than a specified limit 
	(A security bug I saw in my career that affected our systems).    
