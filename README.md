# Aggregation-Service
A micro-service built on top of these Spring-Boot modules: web, webflux, data-jpa.



## This service is part of a distributed system so, in order to use it, you have to first download and start its sibling-service (ie back-end service):

    $ docker --version
        Docker version 19.03.11, build 42e35e6
    $ su
    # docker pull mihaitatinta/wiremock-example:0.0.1
    # docker run -it --rm -p 8080:8080 acf5ec487953ea367271bdd7ed93bfea2ae963b1125e43a4f9ffb9e8cc95fc7a



## Now it's time to download, build, and start THIS service:

    $ git clone https://github.com/ailie/Aggregation-Service.git

    $ cd Aggregation-Service/

    $ mvn --version
        Apache Maven 3.6.1 (Red Hat 3.6.1-5)
        Maven home: /usr/share/maven
        Java version: 1.8.0_265, vendor: Red Hat, Inc, runtime: /usr/lib/jvm/java-1.8.0-openjdk-1.8.0.265.b01-1.fc32.x86_64/jre
        Default locale: en_US, platform encoding: UTF-8
        OS name: "linux", version: "5.7.11-200.fc32.x86_64", arch: "amd64", family: "unix"

    $ mvn package && java -jar target/aggregator-0.0.1-SNAPSHOT.jar || mvn spring-boot:run



## Finally, interact with the service you just started:

    $ curl http://localhost:8765/users
        [{
            "id": "eiohanis",
            "name": "Emil Iohanis"
        },{
            "id": "niliescu",
            "name": "Nicolae Iliescu"
        }]

    $ curl http://localhost:8765/accounts
        Please specify an username, eg: /accounts?user=FOO

    $ curl http://localhost:8765/accounts?user=niliescu
        [{
            "id": "f1580fd6-611a-4cbe-8a17-8efff3226983",
            "userId": "niliescu",
            "update": "2020-08-11T03:52:30.841+0000",
            "name": "Account-niliescu",
            "product": "Gold account.",
            "status": "ENABLED",
            "type": "CREDIT_CARD",
            "balance": 1676.90
        }]

    $ curl http://localhost:8765/transactions
        Please specify an account, eg: /transactions?account=BAR

    $ curl http://localhost:8765/transactions?account=f1580fd6-611a-4cbe-8a17-8efff3226983
        [{
            "id": "003b7bfd-a2ce-4867-8fdc-47d8ccb841bd",
            "accountId": "f1580fd6-611a-4cbe-8a17-8efff3226983",
            "exchangeRate": {
                "currencyFrom": "EUR",
                "currencyTo": "USD",
                "rate": 1.10
            },
            "originalAmount": {
                "amount": 31.43,
                "currency": "USD"
            },
            "creditor": {
                "maskedPan": "XXXXXXXXXX12319",
                "name": "Creditor 12319"
            },
            "debtor": {
                "maskedPan": "XXXXXXXXXX98719",
                "name": "DebtorName 98719"
            },
            "status": "BOOKED",
            "currency": "EUR",
            "amount": 28.57,
            "update": "2020-08-11T03:52:30.853+0000",
            "description": "Mc Donalds Amsterdam transaction - 19"
        },{
            "id": "f4fae0da-4332-4342-8c51-b786f0ecd018",
            "accountId": "f1580fd6-611a-4cbe-8a17-8efff3226983",
            "exchangeRate": {
                "currencyFrom": "EUR",
                "currencyTo": "USD",
                "rate": 1.10
            },
            "originalAmount": {
                "amount": 21.48,
                "currency": "USD"
            },
            "creditor": {
                "maskedPan": "XXXXXXXXXX12333",
                "name": "Creditor 12333"
            },
            "debtor": {
                "maskedPan": "XXXXXXXXXX98733",
                "name": "DebtorName 98733"
            },
            "status": "BOOKED",
            "currency": "EUR",
            "amount": 19.53,
            "update": "2020-08-11T03:52:30.854+0000",
            "description": "Mc Donalds Amsterdam transaction - 33"
        }]
