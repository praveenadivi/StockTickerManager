# StockTickerManager
Initial Plan
1. download a project from SpringInitializer
	a. it should have Spring boot
	
	b. it should have Junit and mockito
	
	c. should use an embedded DB like Derby - the data is stock data optimal for long term storage and frequently prone to be warehoused. 
	Hence a relational DB instead of NoSQL. Also the structure is not epheremal and in a normal environment we will need to have replication. 
	Also, this data does not have long Strings and hence it may not be optimal for 	NOSQL
	
	d. should use Spring data or JPA
	
	e. It will have Services with @Transactional from Spring
	d. It will have a Post method to add the data in a batch format - should use Spring batch or a batch function from Spring data for insertion. 
	The file should be sent via HTTP via a multipart request.
	e. It will have a get API for retrieving the records
	f. It will have another Post API for adding the record  
2. I will plan to enhance this design after the excercise by taking care of the following. I am omitting the following for now.
	a. no HTTPS on the APIs - should normally be handled at the proxy level (Apache)
	
	b. no special Signature calculations are being added. The usecase asks for accomodating multiple users although this kind of use case with bulk updates is best suited to
	another system calling this API in that case I would normally whitelist the IP address and add a signature calculation based on 
		1. time
		2. userId/ SystemId/ ProId
		3. Salt
		4. shared Key
	I would take the above information and verify if the application is authorized and if the request is in a specified time 
	window to be processed (I may not want to process hour old requests).

	c. I am not doing anything preemptive like keeping track of how many requests were made by a user 
	and if the MD5Sum of the file & file name provided was already provided in the recent past. I would normally do this in this kind of 
	an application with bulk uploads and potential for duplicates.
	
	d. I would normally deploy an application like this on a cluster of servers that have 8 GB RAM. These will be supported by a load balancer & proxy that will take care of 
	distributing the requests in a round robin fashion and have a security monitoring software on the proxy that will take care of filtering some potentially harmful requests
	like a apache commons issue like tomcat crashing because the delimiter in the multipart request is bigger than a specified limit 
	(A security bug I saw in my career that affected our systems).    
