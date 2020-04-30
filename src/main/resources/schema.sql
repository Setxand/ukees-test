 create table if not exists department (id varchar(36) not null PRIMARY KEY , name varchar(64));
create table if not exists employee (id varchar(36) not null PRIMARY KEY , email varchar(64), password varchar(64), name varchar(64), active boolean, department_id varchar(36),  FOREIGN KEY (department_id) REFERENCES department(id));


insert into department (id, name) Select '234e324', "department-test1" Where not exists(select * from department where id='234e324')     ;
insert into department (id, name) Select 'sdfsdf', "department-test2" Where not exists(select * from department where id='sdfsdf')  ;
insert into department (id, name) Select '234321', "department-test3" Where not exists(select * from department where id='234321');



insert into employee (id, email, password, name, department_id, active) select "342334dfsgg", "testuser@email.com",   "$2a$10$RQO4tdxxxpD/0NkO053/9.FhQ8N7kEwOGo74HhZwY/brOWvcYlo2y", "Artem",  "234e324", true where not exists(select*from employee where id = "342334dfsgg");
insert into employee (id, email, password, name, department_id, active) select "342324gfdgg", "testuser@email1.com",  "$2a$10$RQO4tdxxxpD/0NkO053/9.FhQ8N7kEwOGo74HhZwY/brOWvcYlo2y", "Artem1", "234e324", true where not exists(select*from employee where id = "342324gfdgg");
insert into employee (id, email, password, name, department_id, active) select "341234gfdgf", "testuser@email2.com",  "$2a$10$RQO4tdxxxpD/0NkO053/9.FhQ8N7kEwOGo74HhZwY/brOWvcYlo2y", "Artem2", "234e324", true where not exists(select*from employee where id = "341234gfdgf");
insert into employee (id, email, password, name, department_id, active) select "34323dfdsf4", "testuser@email3.com",  "$2a$10$RQO4tdxxxpD/0NkO053/9.FhQ8N7kEwOGo74HhZwY/brOWvcYlo2y", "Artem3", "234e324", true where not exists(select*from employee where id = "34323dfdsf4");
insert into employee (id, email, password, name, department_id, active) select "343vfdfds34", "testuser@email4.com",  "$2a$10$RQO4tdxxxpD/0NkO053/9.FhQ8N7kEwOGo74HhZwY/brOWvcYlo2y", "Artem4", "234e324", true where not exists(select*from employee where id = "343vfdfds34");
insert into employee (id, email, password, name, department_id, active) select "342vf3fds4g", "testuser@email5.com",  "$2a$10$RQO4tdxxxpD/0NkO053/9.FhQ8N7kEwOGo74HhZwY/brOWvcYlo2y", "Artem5", "234e324", true where not exists(select*from employee where id = "342vf3fds4g");
insert into employee (id, email, password, name, department_id, active) select "3423fgdsgf4", "testuser@email6.com",  "$2a$10$RQO4tdxxxpD/0NkO053/9.FhQ8N7kEwOGo74HhZwY/brOWvcYlo2y", "Artem7", "234e324", true where not exists(select*from employee where id = "3423fgdsgf4");
insert into employee (id, email, password, name, department_id, active) select "34234dfgfdg", "testuser@email7.com",  "$2a$10$RQO4tdxxxpD/0NkO053/9.FhQ8N7kEwOGo74HhZwY/brOWvcYlo2y", "Artem6", "234e324", true where not exists(select*from employee where id = "34234dfgfdg");
insert into employee (id, email, password, name, department_id, active) select "342fgdgfdg34", "testuser@email8.com", "$2a$10$RQO4tdxxxpD/0NkO053/9.FhQ8N7kEwOGo74HhZwY/brOWvcYlo2y", "Artem7", "234e324", true where not exists(select*from employee where id = "342fgdgfdg34");
insert into employee (id, email, password, name, department_id, active) select "3423gfdgfdg4", "testuser@email9.com", "$2a$10$RQO4tdxxxpD/0NkO053/9.FhQ8N7kEwOGo74HhZwY/brOWvcYlo2y", "Artem8", "234e324", true where not exists(select*from employee where id = "3423gfdgfdg4");