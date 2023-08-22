package com.java;

import java.sql.*;
import java.util.*;

public class Main {
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		int choice=-1,bookid;
		String newbookname,newisbn;
		try {
			//Establishing connection to database
			Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","scott","tiger");
//			System.out.println("Want to create new table Press 1: ");
//			int k=sc.nextInt();
//			if(k==1) {
//				// Drop table
//				try (Statement dropStat = con.createStatement()) {
//	                dropStat.execute("DROP TABLE books");
//	            } catch (SQLException ex) {
//	            }	
//				// Create table 
//		        try (Statement stat = con.createStatement()) {
//		        	String createTableSQL = "CREATE TABLE books (bookid number,bookname varchar(50),isbn varchar(20))";
//		                stat.execute(createTableSQL);
//		        }
//		    }
			while(true) {
				System.out.println("\n=========================================");
				System.out.println("        Book Management System");
				System.out.println("=========================================");
				System.out.println("1. Add a book");
				System.out.println("2. List all books");
				System.out.println("3. Delete a book");
				System.out.println("4. Update a book");
				System.out.println("5. Exit");
				System.out.println("=========================================");
				System.out.println("Enter your choice:(Integer Input) ");
				if(sc.hasNextInt()) {
					choice=sc.nextInt();
				}else {
					System.out.println("Invalid Input..");
					break;
				}
				
				switch(choice) {
				case 1:
					//Adding a Book
					System.out.println("Enter Id(int) ");
					if(sc.hasNextInt()) {
						bookid=sc.nextInt();
					}else {
						System.out.println("Invalid Input..");
						continue;
					}
					sc.nextLine();
					System.out.println("Enter Book Name");
					newbookname=sc.nextLine();
					System.out.println("Enter ISBN:");
					newisbn=sc.nextLine();
					String sql="insert into books (bookid,bookname,isbn) values (?,?,?)";
					try(PreparedStatement stat=con.prepareStatement(sql)){
						stat.setInt(1, bookid);
						stat.setString(2, newbookname);
						stat.setString(3, newisbn);
						stat.executeUpdate();
					}
					System.out.println("Book Added Successfully!!!");
					break;
				case 2:
					//Listing all books
					System.out.println("List of all Books :");
					sql="select * from books";
					try(PreparedStatement stat=con.prepareStatement(sql)){
						ResultSet result=stat.executeQuery(sql);
						while(result.next()) {
							int id1=result.getInt("bookid");
							String name=result.getString("bookname");
							String isbn=result.getString("isbn");
							System.out.println("Book id: "+id1);
							System.out.println("Book name: "+name);
							System.out.println("Book ISBN: "+isbn);
						}
					}
					break;
				case 3:
					//Deleting a book
					System.out.println("Enter Book id to delete");
					
					if(sc.hasNextInt()) {
						bookid=sc.nextInt();
					}else {
						System.out.println("Invalid Input..");
						continue;
					}
					sc.nextLine();
					sql="delete from books where bookid=?";
					try(PreparedStatement stat=con.prepareStatement(sql)){
						stat.setInt(1, bookid);
						int affectedrow=stat.executeUpdate();
						if(affectedrow>0) {
							System.out.println("Book Deleted!!");
						}else {
							System.out.println("Book id not found");
						}
					}
					break;
				case 4:
					//Updating book
					System.out.println("Enter ID to update");
					if(sc.hasNextInt()) {
						bookid=sc.nextInt();
					}else {
						System.out.println("Invalid Input..");
						continue;
					}
					sc.nextLine();
					System.out.println("Enter new Book Name");
					newbookname=sc.nextLine();
					System.out.println("Enter new ISBN ");
					newisbn=sc.nextLine();
					sql="update books set bookname=?,isbn=? where bookid=?";
					try(PreparedStatement stat=con.prepareStatement(sql)){
						stat.setString(1, newbookname);
						stat.setString(2, newisbn);
						stat.setInt(3, bookid);
						int affectedrow=stat.executeUpdate();
						if(affectedrow>0) {
							System.out.println("Book Updated!!");
						}else {
							System.out.println("Book id not found");
						}
					}
					break;
				case 5:
					//Exiting Program
					System.out.println("Exiting...");
					con.close();
					System.exit(0);
					default:
						System.exit(0);
				}
			}
		}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}