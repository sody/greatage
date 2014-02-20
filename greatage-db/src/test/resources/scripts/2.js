//! GA-2
//@ Vasya Pupkin
//# Fake change


//! GA-3
//# Change 3
// some script comments

db.companies.insert({_id: 'company3'});


//! GA-4
//@ Vasya
var cursor = db.companies.find();
while (cursor.hasNext()) {
    var company = cursor.next();
    company.address = 'Belarus';
    db.companies.save(company);
}

//! GA-5
//@ Pupkin
db.companies.insert({_id: 'company5'});
