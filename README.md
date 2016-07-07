# [GreatAge v1.0](http://sody.github.io/greatage) [![Build Status](https://travis-ci.org/sody/greatage.png)](https://travis-ci.org/sody/greatage)

GreatAge is an open source project for building java-based back-end DAO infrastructure.

## Repository
Repository is a main access point for API which function is to offer create, read, update and delete operations(CRUD) for persistable domain objects.

### Create
  Makes entities persistent by saving them within underlying storage engine. It is equal to SQL `INSERT` statement. It can be used for single entity or for more than one entity in batch.

  ```java
  // single operation
  Company company = createCompany();
  repository.create(company);

  // batch operation
  repository.createAll(company1, company2);
  repository.createAll(Arrays.<Company>asList(company1, company2));
  ```

### Read
  Reads entities data from underlying storage engine by primary key. It is equal to SQL `SELECT ... WHERE ID=?` statement. It can be used to retrieve single entity data or to retrieve more that one entity data in batch.

  ```java
  // single operation
  Company company1 = repository.read(Company.class, 12l);
  Company company2 = repository.read(company1);

  // batch operation
  Map<Long, Company> companies;
  companies = repository.readAll(Company.class, 12l, 13l, 14l);
  companies = repository.readAll(Company.class, Arrays.<Long>asList(12, 13, 14));
  companies = repository.readAll(company1, company2);
  companies = repository.readAll(Arrays.<Company>asList(company1, company2));
   ```

### Update
  Updates entities state within underlying storage engine. It is equal to SQL `UPDATE` statement. It can be used for single entity or for more than one entity in batch.


  ```java
  // single operation
  Company company = getCompany();
  repository.update(company);

  // batch operation
  repository.updateAll(company1, company2);
  repository.updateAll(Arrays.<Company>asList(company1, company2));
  ```

### Delete
  Removes entities data from underlying storage engine. It is equal to SQL `DELETE` statement. It can be used for single entity or for more than one entity in batch. It can delete entities by just primary keys.

  ```java
  // single operation
  Company company = getCompany();
  repository.delete(company);
  // by pk
  repository.delete(Company.class, 12l);

  // batch operation
  repository.deleteAll(company1, company2);
  repository.deleteAll(Arrays.<Company>asList(company1, company2));
  // by pk
  repository.deleteAll(Company.class, 12l, 13l, 14l);
  repository.deleteAll(Company.class, Arrays.<Long>asList(12l, 13l, 14l));
  ```

## Query
It is an extension for Repository that provides more flexible way to query persistable domain objects.

### Filtering
  Controls what entries should be filtered by query. It is equal to SQL `WHERE` part of select statement. Filters can be applied for query by one of the following ways:

  ```java
  // by property
  $(Company.class)
      // connjunction is used by default
      .filter("name", "GreatAge") // equal operator is used by default
      .or() // disjunction
        .filter("country.code =", "us") // property of associated object
        .and() // conjunction
          .filter("info.note !=", null) // property of embed object
          .filter("registeredAt <=", new Date())
        .end() // end conjunction
      .end() // end disjunction
      // fetch, sort, pagination
      .list();

  // by criteria mappers
  $(Company.class)
      // connjunction is used by default
      .filter(company$.name$.eq("GreatAge"))
      .filter($.or(
        company$.country$.code$.eq("us"), // property of associated object
        company$.country$.is( // the same
          country$.code$.eq("us")
        )
        $.and(
          company$.info$.note$.isNull() // property of embed object
          company$.info$.is( // the same
            companyInfo$.note$.isNull()
          ),
          company$.registeredAt$.lessThan(new Date())
        )
      ))
      // fetch, sort, pagination
      .list();

  // hybrid
  $(Company.class)
      // connjunction is used by default
      .filter(company$.name$.eq("GreatAge"))
      .or()
        .filter("country.code", "us")
        .filter($.and(
          company$.info$.property("note").isNull()
          company$.registeredAt$.lessThan(new Date())
        ))
      .end()
      // fetch, sort, pagination
      .list();
  ```


### Fetch options
  Defines what fields for the querying entity should be retrieved and what fields should not. In the world of RDBMS this means what columns to include and what association tables to join by select statement. This options can be defined by one of the following ways:

  ```java
  // by property
  $(Company.class)
      // filters, sort, pagination
      .fetch("country") // fetch country
      .fetch("departments", false) // do not fetch departments
      .list();

  // by criteria mapper
  $(Company.class)
      // filters, sort, pagination
      .fetch(company$.country$) // fetch country
      .fetch(company$.departments$, false) // do not fetch departments
      .list();

  // hybrid
  $(Company.class)
      // filters, sort, pagination
      .fetch(company$.property("country")) // fetch country
      .fetch(company$.property("departments"), false) // do not fetch departments
      .list();
  ```

### Sort options
  Controls the order that the query returns matching entries. It is equal to using `SORT BY` in SQL databases. This options can be defined by one of the following ways:

  ```java
  // by property
  $(Company.class)
      // filter, fetch options, pagination
      .sort("name") // sort by name ascending
      .sort("description", false) // sort by description descending
      .list();

  // by criteria mapper
  $(Company.class)
      // filter, fetch options, pagination
      .sort(company$.name$) // sort by name ascending
      .sort(company$.description$, false) // sort by description descending
      .list();

  // hybrid
  $(Company.class)
      // filter, fetch options, pagination
      .sort(company$.property("name")) // sort by name ascending
      .sort(company$.property("description"), false) // sort by description descending
      .list();
  ```

### Pagination
  Defines how many entries should be skipped before retrieving result and the maximum number of etries to retrieve. It is analogous to the `LIMIT` statement in a SQL database. This options can be defined by one of the following ways:

  ```java
  // separate skip and limit
  $(Company.class)
      // filter, fetch options, sort
      .skip(10) // skip first 10 results
      .limit(100) // limit result by 100 rows
      .list();

  // skip and limit alltogether
  $(Company.class)
      // filter, fetch options, sort
      .paginate(10, 100) // start from 10 and limit by 100 entries
      .list();
  ```

### Count
  Counts matching entries. This query execution mode will ignore all query options except filters.

  ```java
  // retrieve company count
  long count = $(Company.class)
      // filter
      .count();
  ```

### Single entry retrieval
  Retrieves single matching entry. This query execution mode will ignore pagination options. It can be performed by two ways:

  ```java
  // safe mode
  // exception will be thrown
  // if none or more that one entry matches filter
  Company company = $(Company.class)
      // filter, fetch options, sort
      .unique();

  // unsafe mode
  // it will always retrieve the very first matching entry
  // and return null if no entry matches
  Company company = $(Company.class)
      // filter, fetch options, sort
      .first();
  ```

### Multiple entries retrieval
  Retrieves multiple matching entries as list. This query execution mode will use all query options. It can be performed by two ways:

  ```java
  // full entry retrieval mode
  // it will obtain full populated entries
  List<Company> companies = $(Company.class)
      // filter, fetch options, sort, pagination
      .list();

  // primary keys mode
  // it will obtain just entry primary keys
  List<Long> keys = $(Company.class)
      // filter, fetch options, sort, pagination
      .keys();
  ```

### Multiple entries lazy retrieval
  Retrieves multiple matching entries as lazy iterable that will load entries in batches by need. This query execution mode will use all query options. Like previous mode it can be performed by two ways:

  ```java
  // full entry retrieval mode
  // it will obtain full populated entries
  for (Company company : $(Company.class)
      // filter, fetch options, sort, pagination
      .iterate()) {
    // do somathing with company
  }

  // primary keys mode
  // it will obtain just entry primary keys
  for (Long id : $(Company.class)
      // filter, fetch options, sort, pagination
      .keys()) {
    // do something with id
  }
  ```

## Criteria mappers
It is a set of helper classes that simplifies usage of query.

- Property mapper
  Provides possibility to map entity properties for later use in query. Includes all basic filter operations with single entity property: `=`, `!=`, `<`, `<=`, `>`, `>=`, `is null`, `not null`, `in`, `not in`, `like`

- Embed mapper
  Provides possibility to collect embed object property mappers for later use in query. It can be used inside another citeria mapper or as a separate root object mapper.

- Entity mapper
  Provides possibility to collect entity property mappers for later use in query. It can be used inside another citeria mapper or as a separate root object mapper.

- Collection mapper

- Root mapper
  It is a main access point for all other criteria mappers. It usually has name `$` and contains all other root object mappers.