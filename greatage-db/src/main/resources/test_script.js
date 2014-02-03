//! change-set-id
var cursor = db.companies.find();
while (cursor.hasNext()) {
    var company = cursor.next();
    company.address = 'Belarus';
    db.companies.save(company);
}
