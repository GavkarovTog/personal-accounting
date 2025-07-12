In this project I aim to create multi-user application for personal incomes and expenses accounting. 
Application must allow user to manage accounts, expense/income categories and operations on accounts.
This project is for learning purposes and uses such technologies as Spring framework, Spring Boot, Spring Security, Spring Data JPA, Hibernate and PostgreSQL.

Personal accounting application is under development right now and it is newer version of old archived personal accounting application that I developed earlier: https://github.com/GavkarovTog/personal-accounting-deprecated

In older version I used the same technologies as now, but I move forward in idea evolution. Current project has new features as:
1) Multiple users;
2) Implemented authentication and authorization: registration, login and logout processing;
3) I use session scope bean to contain current user settings in RAM, use validator beans to validate forms, so I'm getting more acquainted with Spring IOC container;
4) I use filter to redirect user to settings page after registration and login(if he doesn't have setup already), so I'm getting more acquainted with Spring Security architecture;
5) I manually create schema for database instead of automatic Hibernate generation to understand ORM better;
6) I use not only HTML, but CSS and some JavaScript(HTML content is now populated on server-side, but in next project iteration I'm planning to use react);
7) I use Hibernate entity inheritance to treat accounts and categories the same way since in old project I learnt that it would be better for operation processing if categories were like accounts with zero balance;
8) Time zones, currencies, date formats - application became more international with corresponding problems arise which I learn to handle.

For this application I'm planning to:
1) have some analytics with filtration on operation type, category and accounnts;
2) application to be deployed on external Internet so I will learn how to deploy application with https configuration and others;

Here are some screens of application:
![image](https://github.com/user-attachments/assets/ef953c1b-8bf0-49a3-aaa2-d3f849e3df2c)

![image](https://github.com/user-attachments/assets/61d7815f-337e-464d-98c3-08587d3fd407)

![image](https://github.com/user-attachments/assets/e2dc1eff-d891-440d-b1f9-c6ac18d91b0c)
