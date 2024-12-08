db = db.getSiblingDB('admin');
db.auth('root', '123456');

db = db.getSiblingDB('bookstore');
print('Creating user for bookstore database');
db.createUser({
  user: 'bookstore',
  pwd: '123456',
  roles: [
    {
      role: 'readWrite',
      db: 'bookstore',
    },
  ],
});
print('User created successfully');

db.createCollection('comment');