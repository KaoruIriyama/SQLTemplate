package model;

public class Person {
 private int id;
 private String name;
 private int age;
 private Gender gender;
// Genderは列挙型で定義するが、データベース内では文字列とする
// (Genderのフィールド変数を当てはめる)
 //null安全
	public int getId(){
		return this.id; 
	} 
	 
	public String getName() {
		return this.name;
	}
	
	public int getAge() {
		return this.age;
	}
	
	public Gender getGender() {
		return this.gender;
	}
	
	public Person(String name, int age, Gender gender) {

			this.name = name;
			this.age = age;
			this.gender = gender;
			
	}
	
	
	public Person(int id, String name, int age, Gender gender) {
			
			this.id = id;
			this.name = name;
			this.age = age;
			this.gender = gender;
		
	}
	
	@Override
	public String toString() {
		return "ID:" + this.getId() + ", 名前:" + this.getName() + 
				", 年齢:" + this.getAge() + ", 性別:" + getGender().getName() ;
	}

	public void setName(String name) {
		this.name = name;
		//テスト用
	}
	
	
}
