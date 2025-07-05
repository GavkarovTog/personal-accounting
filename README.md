In this projects I aim to create multi-user application for personal incomes and expenses accounting. 
Application must allow user to manage accounts, expense and income categories, operations on accounts for categories.
This project is for learning purposes and uses such technologies as Spring framework, Spring boot, Spring Security, Spring Data JPA, Hibernate and PostgreSQL.

Personal accounting application is under development right now and it is newer version of old archived personal accounting application that I developed earlier: https://github.com/GavkarovTog/personal-accounting-deprecated

In older version I used the same technologies as now, but I move forward in idea evolution. Current project has new features as:
1) Multiple users;
2) Implemented authentication and authorization, login and logout processing;
3) I manually create schema for database instead of automatic Hibernate generation to understand ORM better;
4) I use not only HTML, but CSS and some JavaScript(HTML content is now populated on server-side, but in future I'm planning to use react);
5) I use Hibernate entity inheritance to treat accounts and categories the same way since in old project I learnt that it would be better if categories were like accounts with zero balance for operation processing;
6) 
