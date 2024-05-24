package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Gender;
import model.Person;

public class PersonDAO {
	//JDBCドライバとの接続に使う情報
//	private final String JDBC_URL = "jdbc:h2:tcp://localhost/~/dokoTsubu";
	private final String JDBC_URL = "jdbc:h2:~/dokoTsubu";
	//jdbc:h2:tcp://localhost/~/dokoTsubuだったときはテスト失敗
	//jdbc:h2:~/dokoTsubuにURLを変更したらテストが通った
	private final String DB_USER = "sa";
	private final String DB_PASS = "";
	private PreparedStatement pstmt = null;
	private String sql;
	private ResultSet rs = null;
	private static Connection conn;
	
	Person person;
	
	static {
		try {
			Class.forName("org.h2.Driver");
			} catch(ClassNotFoundException e) {
				throw new IllegalStateException("JDBCドライバを読み込めませんでした");
			} 
	}
	
	private void connectDB(){
		
		try {
			conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void closeConnection() {
		
		try {
			if(pstmt != null) {
				pstmt.close();
				}
			if(rs != null) {
				rs.close();
				}
			if(conn != null) {
			conn.close();
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void checkPerson(Person person) {
		if(person.getName() == null){
			throw new NullPointerException("人物名は必ず指定してください");
		}else if(person.getAge() > 120 || person.getAge() <= 0) {
			throw new IllegalArgumentException("年齢は0歳以上120以下の数字を指定してください");
		}else if(person.getGender() == null) {
			throw new NullPointerException("性別は必ず指定してください");
		}
	}
	
	
	private void checkID(int id) {
		if(id <= 0) {
			throw new IllegalArgumentException("人物IDには1以上の数字を指定してください");
		}
	}
	
	
	public boolean confirmPerson(Person person) {
		try {
			this.checkPerson(person);
		}catch(IllegalArgumentException iae) {
			iae.printStackTrace();
			return false;
		}catch(NullPointerException npe) {
			npe.printStackTrace();
			return false;
		}return true;
	}
	
	
	public boolean confirmID(int id) {
		try {this.checkID(id);}
		catch(IllegalArgumentException iae) {
			iae.printStackTrace();
			return false;
		}return true;
	}
	
	
	public boolean recordPerson(Person person) {	
		
		if(confirmPerson(person) == false) {
			return false;
		}else {
			try{
				this.connectDB();
				sql = "INSERT INTO PERSON(NAME, AGE, GENDER) VALUES(?, ?, ?)";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, person.getName());
				pstmt.setInt(2, person.getAge());
				pstmt.setString(3, person.getGender().toString());
	//			pstmt.setString(3, person.getGender().getName());
				//定数名でなく変数名を入れる
				
				int result = pstmt.executeUpdate();
				return (result == 1);
				
			}catch(SQLException e) {
				e.printStackTrace();
				System.out.println("recordPerson()の実行中に例外が発生しました。");
				return false;
			}
			finally {
				this.closeConnection();
			}
		}
	}
	
    public List<Person> selectAll() {
    	List<Person> personList = new ArrayList<>();
		try{
			this.connectDB();
			sql = "SELECT ID, NAME, AGE, GENDER, FROM PERSON ORDER by ID DESC";
			pstmt = conn.prepareStatement(sql);
								
			rs = pstmt.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("ID");
				String name = rs.getString("NAME");
				int age = rs.getInt("AGE");
				Gender gender = Gender.valueOf(rs.getString("GENDER"));
				//これだと定数名がそのまま出る
//				valueOf() String から enum への変換
//				列挙型の定数からフィールド変数を呼び出す
				Person person = new Person(id, name, age, gender);
				personList.add(0,person);
				//personList.add(person);だとリストがIDの大きい順に表示されるので,
				//リストの先頭に追加することで取り出すとき番号の小さい順になった
			}	
		}catch(SQLException e) {
			e.printStackTrace();
			System.out.println("selectAll()の実行中に例外が発生しました。");
			return null;
		}
		finally {
			this.closeConnection();
		}
		return personList;
	}
     
    public Person selectPerson (int num) {
    	
    	if(confirmID(num)) {
	    	try{
	    		this.connectDB();
	    		sql = "SELECT ID, NAME, AGE, GENDER, FROM PERSON WHERE ID = ?";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setInt(1, num);
				
				rs = pstmt.executeQuery();
				while(rs.next()) {
					try{
						int id = rs.getInt("ID");
					    String name = rs.getString("NAME");
					    int age = rs.getInt("AGE");
					    Gender gender = Gender.valueOf(rs.getString("GENDER"));
	
					person = new Person(id, name, age, gender);
					}catch(NullPointerException e) {
						e.printStackTrace();
						System.out.println("Personの取得に失敗しました。");
						return null;
					}
				}
	    	}catch(SQLException e) {
	    		e.printStackTrace();
	    		System.out.println("selectPerson()の実行中に例外が発生しました。");
	    		return null;
	    	}
	    	finally {
				this.closeConnection();
			}
    	}
    	return person;
    }
    
	public List<Person> selectPersonList (List<Integer> idList) {
			for(Integer i : idList) {
				if(confirmID(i) == false) {return null;}
			}
	//    	引数内のIntegerに対してconfirmId()を実行、falseならnullを返す
		
			List<Person> plist = new ArrayList<>();
		
	    	try{
	    		this.connectDB();
	   
	    		StringBuilder sb = new StringBuilder();
	
	    		for(Integer i : idList){ sb.append("?,");	}
	    		String placeHolder = sb.deleteCharAt( sb.length() -1 ).toString();
	    		//これでsbの末尾にある","を消すことが出来る
	   		
	    		sql = "SELECT ID, NAME, AGE, GENDER, FROM PERSON WHERE ID IN(" +placeHolder+ ")";
	    		pstmt = conn.prepareStatement(sql);
	  
	    		int j = 1;
	    		for(Integer i : idList){
	    		    pstmt.setString(j, i.toString());  
	    		    j++;
	    		}
	//    		System.out.println(pstmt);
				
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					try{
						int id = rs.getInt("ID");
					    String name = rs.getString("NAME");
					    int age = rs.getInt("AGE");
					    Gender gender = Gender.valueOf(rs.getString("GENDER"));
	
					    person = new Person(id, name, age, gender);
					
					    plist.add(person);
					}catch(NullPointerException e) {
						e.printStackTrace();
						System.out.println("Personの取得に失敗しました。");
						return null;
					}
				}
	    	}catch(SQLException e) {
	    		e.printStackTrace();
	    		System.out.println("selectPersonList()の実行中に例外が発生しました。");
	    		return null;
	    	}
	    	finally {
	    		closeConnection();
	    	}
	    	return plist;
	    }
    
    public int selectMAXID() {
    	
    	int MAXID = 0;
    	
    	try {
    		this.connectDB();
    		sql = "SELECT MAX(ID) FROM PERSON";
    		pstmt = conn.prepareStatement(sql);
    		rs = pstmt.executeQuery();
    		if(rs.next()) {
    			MAXID = rs.getInt("MAX(ID)");
    			}else {    					
        			System.out.println("最大IDの取得に失敗しました。");
    			}
    	}catch(SQLException e) {
    		e.printStackTrace();
    		System.out.println("selectMAXID()の実行中に例外が発生しました。");
    	}finally {
			this.closeConnection();
		}return MAXID;
    }
//未使用(2024/05/18追記)
    public List<Person> selectPersonByName (String keyname) {
    	if(keyname == null){return null;}
    	else {
    		List<Person> plist = new ArrayList<>();
    		try{
	    		this.connectDB();
	   		
	    		sql = "SELECT ID, NAME, AGE, GENDER, FROM PERSON WHERE NAME = ?";
	    		pstmt = conn.prepareStatement(sql);
	    		pstmt.setString(1, keyname);				
	    		rs = pstmt.executeQuery();
				
				while(rs.next()) {
					try{
						int id = rs.getInt("ID");
					    String name = rs.getString("NAME");
					    int age = rs.getInt("AGE");
					    Gender gender = Gender.valueOf(rs.getString("GENDER"));
	
					    person = new Person(id, name, age, gender);
					
					    plist.add(person);
					}catch(NullPointerException e) {
						e.printStackTrace();
						System.out.println("Personの取得に失敗しました。");
						return null;
					}
				}
	    	}catch(SQLException e) {
	    		e.printStackTrace();
	    		System.out.println("selectPersonByName()の実行中に例外が発生しました。");
	    		return null;
	    	}
    		finally {
	    		closeConnection();
	    	}
	    	return plist;
    	}
    }
  //未使用(2024/05/18追記)  
	public boolean updatePerson (Person person) {
		
		if(confirmPerson(person) == false ||  confirmID(person.getId()) == false) {
			return false;
		}else{	
			try {
				this.connectDB();
				sql = "UPDATE PERSON SET NAME = ? , AGE = ? , GENDER = ? WHERE ID = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, person.getName());
				pstmt.setInt(2, person.getAge());
				pstmt.setString(3, person.getGender().toString());
				pstmt.setInt(4, person.getId());
				
				int result = pstmt.executeUpdate();
				return (result == 1);//一行分更新出来たら成功している
				
			}catch(SQLException e) {
				e.printStackTrace();
				System.out.println("updatePerson()の実行中に例外が発生しました。");
				return false;
			}
			finally {
				this.closeConnection();
			}
		}
	}
	//plist内のIDを主キーとして、かくPERSONレコードの更新を行う
	
	public boolean updatePersonList(List<Person> plist) {

		if(plist.stream().allMatch(p -> confirmPerson(p)) && 
				plist.stream().allMatch(p -> confirmID(p.getId()))) {
			try {
				this.connectDB();
				
				StringBuilder sbid = new StringBuilder();
				StringBuilder sbname = new StringBuilder();
				StringBuilder sbage = new StringBuilder();
				StringBuilder sbgender = new StringBuilder();
	    		for(Person p : plist) {
	    			sbid.append(p.getId()).append(",");	
	    			sbname.append("WHEN ").append(p.getId()).append(" THEN '").append(p.getName()).append("' ");
	    			sbage.append("WHEN ").append(p.getId()).append(" THEN ").append(p.getAge()).append(" ");
	    			sbgender.append("WHEN ").append(p.getId()).append(" THEN '").append(p.getGender()).append("' ");
	    		}	
	    		
	    		String idHolder = sbid.deleteCharAt( sbid.length() -1 ).toString();
	    		
	    		//これでsbの末尾にある","を消すことが出来る
	    		
	    		String nameHolder = "CASE ID " + sbname.toString() + "END, ";
	    		String ageHolder = "CASE ID " + sbage.toString() + "END, ";
	    		String genderHolder = "CASE ID " + sbgender.toString() + "END ";
	    		
				sql = "UPDATE PERSON SET "
							+ "NAME = " + nameHolder
							+ "AGE = " + ageHolder
							+ "GENDER = " + genderHolder
							+ "WHERE ID IN (" + idHolder+ ")";
			
			
				pstmt = conn.prepareStatement(sql);				
				
				int result = pstmt.executeUpdate();
				return (result == plist.size());
				
			}catch(SQLException e) {
				e.printStackTrace();
				System.out.println("updatePersonList()の実行中に例外が発生しました。");
				return false;
			}
			finally {
				this.closeConnection();
			}
		}else {return false;}
	}
	//未使用(2024/05/18追記)
	public boolean deletePerson(int num) {
		if(confirmID(num) == false) {return false;}
		else {
			try{
				this.connectDB();
				sql = "DELETE FROM PERSON WHERE ID = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, num);
				
				int result = pstmt.executeUpdate();
				return (result == 1);
				
			}catch(SQLException e) {
				e.printStackTrace();
				System.out.println("deletePerson()の実行中に例外が発生しました。");
				return false;
			}
			finally {
				this.closeConnection();
			}
		}
	}
	
	public boolean deletePersonList(List<Integer> idList) {
		if(idList.stream().allMatch(id -> confirmID(id))) {
		
			try{
				this.connectDB();
				   
	    		StringBuilder sb = new StringBuilder();
	
	    		for(Integer i : idList){ sb.append("?,");	}
	    		String placeHolder = sb.deleteCharAt( sb.length() -1 ).toString();
	    		//これでsbの末尾にある","を消すことが出来る
	   		
	    		sql = "DELETE FROM PERSON WHERE ID IN(" +placeHolder+ ")";
	    		pstmt = conn.prepareStatement(sql);
	  
	    		int j = 1;
	    		for(Integer i : idList){
	    			 pstmt.setString(j, i.toString());  
	    		    j++;
	    		}
				int result = pstmt.executeUpdate();
				return (result == idList.size());
				
			}catch(SQLException e) {
				e.printStackTrace();
				System.out.println("deletePersonList()の実行中に例外が発生しました。");
				return false;
			}
			finally {
				this.closeConnection();
			}
		}else {return false;}
	}
	

}
