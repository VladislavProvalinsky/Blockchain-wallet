# About project
Blockchain: electronic wallet and mining service (Study project in IT-Academy (Belarus Hi Tech Park). 
Electronic wallet - Spring MVC web interface for managing transactions and initiating a mining session. 
Mining service - Spring Boot application generating blocks for mining sessions on a multithreading basis and verifying the consistency of the chain.
Database - MySQL, encryptor - Bitcoinj/BouncyCastle, testing - JMeter.

#How to use
Go to the home page localhost:8080/blockchain/ and you will be redirected to the HTTPS connection. Ignore the browser blocking and continue connection (system uses SSL).
You will pass to the https://localhost:8443/blockchain/ start page.
Register new wallet. After succesfull passing registration system will generate on your PC desctop new directory with Private-Key txt file in it. This key is your digital signature for every operation
for every wallet operations. Keep it secured and unmodified because without right Private-Key verification mechanism will abort all your transactions and you will lost all your money!

#How to use Mining Engine
For each user, the system provides the opportunity to use its own Mining Engine for bitcoin mining. The main tasks performed by the mining mechanism, which is under the control 
of users, are the verification of unconfirmed transactions of the system and the verification of their digital signature and hash values, as well as the generation of new blocks 
in the blockchain system with the calculation of the unique key value using a hash algorithm. You can independently operate your mining process by activating or stopping it by 
pressing the appropriate buttons.
By default, regardless of the users, the system checks the integrity of the block tree for hacker attacks in order to replace the values in the blocks. If a mismatch is detected 
in the blockchain chain, the system crashes its process and displays the message which block has been changed.

#Summary
This is my first open source training project, ready for something more. 
If you are interested in this project and you also want to take part in it to improve its functionality, write to me by email vprovalinsky31@mail.ru or contact me via 
linkedin www.linkedin.com/in/vprovalinsky
I will be happy to cooperate.
